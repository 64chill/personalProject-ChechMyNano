package com.example.checkmynano;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.checkmynano.editAddRemoveActivity.AddRemoveNanoActivity;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    RadioGroup radio_group;
    RadioButton radio_button;

    //
    String checkbox_checked;
    RadioButton checkedBtn;


    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_options, container, false);

        /*ADD NANO ADDR---------------------------------------------------------------------------*/
        Button btnAddAdr = v1.findViewById(R.id.fragment_options_editAdr);
        btnAddAdr.setOnClickListener(this);

        /*radioBtn--------------------------------------------------------------------------------*/
        /*set checked Btn*/
        SetCheckedButton(v1, this.getActivity());
        /*Get which one user chose*/

        Button buttonChangeCurrency = v1.findViewById(R.id.fragment_options_radio_btn);

        buttonChangeCurrency.setOnClickListener(this);

        return v1;
    }
/** Override onClick from View.OnClickListener----------------------------------------------------*/
//todo

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //radio buttons - currency select
            case R.id.fragment_options_radio_btn:
                radio_group = getActivity().findViewById(R.id.radioGroup1);
                int r_id = radio_group.getCheckedRadioButtonId();
                radio_button = getActivity().findViewById(r_id);
                checkbox_checked = radio_button.getText().toString();
                Toast.makeText(getActivity(), "Selected: " +checkbox_checked, Toast.LENGTH_SHORT).show();
                writeToFileBaseCurrency(checkbox_checked, this.getActivity());
                    break;
            // add an address to file
            case R.id.fragment_options_editAdr:
                try {
                    Intent intent = new Intent(getActivity(), AddRemoveNanoActivity.class);
                    startActivity(intent);
                }catch(Exception e){
                    Log.e("intent", e.getMessage());
                }
             break;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    private void writeToFileBaseCurrency(String inputCuurrency, Context context){
        try {
            SharedPreferences.Editor editor = context.getSharedPreferences("BaseCurrencyConfig", MODE_PRIVATE).edit();
            editor.putString("name", inputCuurrency);
            editor.apply();
        } catch (Exception e){
            Log.e("Error WritePrefferences" , e.getMessage());
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    //writeAddAdr
////////////////////////////////////////////////////////////////////////////////////////////////////

    private void SetCheckedButton(View v1, Context context){
        //Get Chosen Currency From File
        String basecurrency="USD";
        try {
            SharedPreferences prefs = context.getSharedPreferences("BaseCurrencyConfig", MODE_PRIVATE);
            String restoredText = prefs.getString("name", null);
            if (restoredText != null) {
                basecurrency = prefs.getString("name", "USD");//"No name defined" is the default value.
            }
        } catch(Exception e){
            Log.e("readBaseCurrency", e.getMessage());
        }
        // set button as checked
        if(basecurrency.trim().equals("USD")){
            checkedBtn = v1.findViewById(R.id.fragment_options_radio_USD);
            checkedBtn.setChecked(Boolean.parseBoolean("true"));
        }
        else if(basecurrency.trim().equals("EUR")){
            checkedBtn = v1.findViewById(R.id.fragment_options_radio_EUR);
            checkedBtn.setChecked(Boolean.parseBoolean("true"));
        }
        else if(basecurrency.trim().equals("CNY")){
            checkedBtn = v1.findViewById(R.id.fragment_options_radio_CNY);
            checkedBtn.setChecked(Boolean.parseBoolean("true"));
        }
        else if(basecurrency.trim().equals("BTC")){
            checkedBtn = v1.findViewById(R.id.fragment_options_radio_BTC);
            checkedBtn.setChecked(Boolean.parseBoolean("true"));
        }
    }
}
