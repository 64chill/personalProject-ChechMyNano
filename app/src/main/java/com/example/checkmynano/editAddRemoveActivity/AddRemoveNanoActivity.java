package com.example.checkmynano.editAddRemoveActivity;
/***
 *  MY METHODS:
 *      public void      dialogAdd          (final Context context)
 *      public boolean   checkIfValidNano   (String userInput)
 *      public void      addDataToDB        (String data, Context context)
 *      public void      dialogRemove       (final Context context, final String recordName)
 *      public void      showLiveAddrList   (final Context context)
 *
 *
 *
 ***/

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.checkmynano.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRemoveNanoActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    Button addBtn;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_nano);

        mListView = this.findViewById(R.id.edit_listview);
        showLiveAddrList(AddRemoveNanoActivity.this);


        addBtn = this.findViewById(R.id.addAdrBtn);
        dialogAdd(AddRemoveNanoActivity.this);


    } //end  oncreate

    /// methods

    /***DialogAdd *********************************************************************************/

    public void dialogAdd(final Context context){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(context); //(CONTEXT!!!)
                View v2 = getLayoutInflater().inflate(R.layout.dialog_add_address, null); // resource file | view group
                final EditText addNanoAddress = v2.findViewById(R.id.dialog_address);
                Button dialog_btnAdd = v2.findViewById(R.id.dialog_btnAdd);

                myBuilder.setView(v2);
                final AlertDialog dialog= myBuilder.create();
                dialog.show();

                dialog_btnAdd.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(!addNanoAddress.getText().toString().isEmpty()){
                            if(checkIfValidNano(addNanoAddress.getText().toString().trim())) {
                                addDataToDB(addNanoAddress.getText().toString().trim(), context);
                                dialog.cancel();
                            } else{Toast.makeText(context, "Please enter the real nano address", Toast.LENGTH_SHORT).show();}
                        } else{
                            Toast.makeText(context, "Please enter the real nano address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    /***checkifValid ******************************************************************************/

    public boolean checkIfValidNano(String userInput) {

        String pattern = "^(xrb|nano)(_)(1|3)[13456789abcdefghijkmnopqrstuwxyz]{59}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(userInput);
        if(m.find()) {
            return true;
        }else {
            return false;
        }
    }

    /***addDataToDB********************************************************************************/
    public void addDataToDB(String data, Context context){
        boolean checker = mDatabaseHelper.checkIfExist(data);
        if(checker) {
            Toast.makeText(context, "You already added this address! Please enter the new one!", Toast.LENGTH_LONG).show();
            return;
        }
        boolean insertData = mDatabaseHelper.addData(data);

        if(insertData){
            Toast.makeText(context,"Your address was added", Toast.LENGTH_LONG).show();
            showLiveAddrList(context);
        } else{
            Toast.makeText(context,"There was a problem with adding the address", Toast.LENGTH_LONG).show();
        }
    }

    /*** DialogRemove *************************************************************************/
    public void dialogRemove(final Context context, final String recordName){
        recordName.trim();
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(context);
        View v3 = getLayoutInflater().inflate(R.layout.dialog_delete_record, null);
        final Button dialog_btnRemove = v3.findViewById(R.id.dialog_delete_remove_btn);
        final Button dialog_btnCancel = v3.findViewById(R.id.dialog_delete_cancel_btn);
        final TextView nanoRecordName = v3.findViewById(R.id.dialog_delete_textView2);
        nanoRecordName.setText(recordName);

        myBuilder.setView(v3);
        final AlertDialog dialog= myBuilder.create();
        dialog.show();

        dialog_btnRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteData(recordName);
                showLiveAddrList(context);
                Toast.makeText(context, "Removed." , Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog_btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
    /*** showLiveAddrList *************************************************************************/

    public void showLiveAddrList(final Context context){
        Cursor addr = mDatabaseHelper.getData();
        ArrayList<String> ListAddr = new ArrayList<>();
        while(addr.moveToNext()){
            ListAddr.add(addr.getString(0));
        }

        //create the list adapter and set the adapter
        android.widget.ListAdapter adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, ListAddr);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i , long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                dialogRemove(context, name);
            }
        });
    }







}