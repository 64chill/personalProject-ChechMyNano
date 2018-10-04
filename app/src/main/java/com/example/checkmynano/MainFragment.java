package com.example.checkmynano;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.checkmynano.Classes.NanoAddressList;
import com.example.checkmynano.addrListAdapter.nanoAddressListAdapter;
import com.example.checkmynano.editAddRemoveActivity.DatabaseHelper;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */

public class MainFragment extends android.support.v4.app.Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    View view;
    ListView nanoListView;
    NanoAddressList nanoAddressList;
    TextView sumNanoView;
    TextView sumBaseCurrencyView;
    DatabaseHelper mDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        mDatabaseHelper  = new DatabaseHelper(getActivity());

        nanoListView = (ListView) view.findViewById(R.id.fragment_main_lvAddr);

        sumNanoView = view.findViewById(R.id.fragment_main_nano_sum);
        sumBaseCurrencyView = view.findViewById(R.id.fragment_main_usd_sum);
        new MyTask().execute();


        return view;
    }
    /**********************************************************************************************/
    /*** MyTask ******************************************************************************/
    /**********************************************************************************************/

    class MyTask extends AsyncTask<Void, Void, Void> {
        String baseCurrency;
        @Override
        protected void onPreExecute() {

        }
//////////////////////
        @Override
        protected Void doInBackground(Void... voids) {
            baseCurrency = readBaseCurrencyFromSharedPreferences(getActivity());
            nanoAddressList = new NanoAddressList(getActivity(), baseCurrency, readSQLgetAdrArray(getActivity()));
            return null;
        }
/////////////////////
        @Override
        protected void onPostExecute(Void Void) {
            try {
                sumNanoView.setText(String.valueOf(nanoAddressList.getSumNano()) + " NANO");
                sumBaseCurrencyView.setText(nanoAddressList.getSumBaseCurreny().toString() + " " + nanoAddressList.getBaseCurrencyType());
                ListAdapter customAdapter = new nanoAddressListAdapter(getActivity(), R.layout.adapter_view_layout_main, nanoAddressList.getNano_address_list(), nanoAddressList.getBaseCurrencyType());
                nanoListView.setAdapter(customAdapter);
            } catch (Exception e) {
                Log.e("onPostExecute", e.getMessage());
            }
        }

    }

    /***readBaseCurrencyFromFile ******************************************************************/
    private String readBaseCurrencyFromSharedPreferences(Context context){
        String baseCurrName="USD";
        try {
            SharedPreferences prefs = context.getSharedPreferences("BaseCurrencyConfig", MODE_PRIVATE);
            String restoredText = prefs.getString("name", null);
            if (restoredText != null) {
                baseCurrName = prefs.getString("name", "USD");//"No name defined" is the default value.
            }
        } catch(Exception e){
            Log.e("readBaseCurrency", e.getMessage());
        }
        return baseCurrName.trim();
    }
    /***readSQLgetAdrArray ************************************************************************/

    private ArrayList<String> readSQLgetAdrArray(Context context){
        ArrayList<String> ListAddr = new ArrayList<>();
        Cursor mCoursor = mDatabaseHelper.getData();

        while(mCoursor.moveToNext()){
            ListAddr.add(mCoursor.getString(0));
        }
        return ListAddr;
    }
}


