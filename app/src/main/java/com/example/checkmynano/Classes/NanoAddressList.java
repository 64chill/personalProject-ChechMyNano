package com.example.checkmynano.Classes;

import android.content.Context;
import android.util.Log;

import com.example.checkmynano.editAddRemoveActivity.DatabaseHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/***
* public class NanoAddressList
*   //contains list of nano addresses and additional features
*
*   public                          NanoAddressList         (Context context,String baseCurrencyType, ArrayList<String> nano_address_list_tmp)
*   public  ArrayList<NanoAddress>  getNano_address_list    ()
*   public  String                  getBaseCurrencyType     ()
*   public  double                  getConversionRate       ()
*   public  BigDecimal              getSumNano              ()
*   public  BigDecimal              getSumBaseCurreny       ()
*   private void                    setListNanoAddress      (ArrayList<NanoAddress> input)
*   private void                    setBaseCurrencyType     (String baseCurrencyType)
*   private void                    setConversionRate       ()
*   public  void                    setSumNano              ()
*   public  void                    setSumBaseCurreny       ()
*   private double                  getRateFromApi          (String base_currency)
*   private static                  String getHTML          (String urlToRead) throws Exception
*
*
*
*
*
*
*
*
*
***/

public class NanoAddressList {
    private ArrayList<NanoAddress> nano_address_list = new ArrayList<>();
    private String baseCurrencyType;
    private double conversionRate;
    private Context context;
    private BigDecimal sumNano = BigDecimal.valueOf(0.0);;
    private BigDecimal sumBaseCurreny = BigDecimal.valueOf(0.0);
    DatabaseHelper mDatabaseHelper;



    public NanoAddressList(Context context,String baseCurrencyType, ArrayList<String> nano_address_list_tmp){
        this.baseCurrencyType=baseCurrencyType;
        this.setConversionRate();
        for(String elem: nano_address_list_tmp ){
            this.nano_address_list.add(new NanoAddress(elem,this.conversionRate, this.context ));
        }
        //

        this.context = context;
        this.setSumNano();
        this.setSumBaseCurreny();

    }
    /*----------------------------------------------------------------------------------------------
     * GET + SET methods
     *---------------------------------------------------------------------------------------------*/
    public ArrayList<NanoAddress> getNano_address_list() {
        return this.nano_address_list;
    }

    public String getBaseCurrencyType() {
        return this.baseCurrencyType;
    }

    public double getConversionRate() {
        return this.conversionRate;
    }
    public BigDecimal getSumNano(){
        return this.sumNano;
    }
    public BigDecimal getSumBaseCurreny(){
        return this.sumBaseCurreny;
    }
//set
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void setListNanoAddress(ArrayList<NanoAddress> input){
        this.nano_address_list = input;
    }

    private void setBaseCurrencyType(String baseCurrencyType) {
        this.baseCurrencyType = baseCurrencyType;
    }

    private void setConversionRate() {
        this.conversionRate = getRateFromApi(this.baseCurrencyType);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setSumNano(){
            Iterator<NanoAddress> itr = this.nano_address_list.iterator();
            while (itr.hasNext()) {
                NanoAddress adr = itr.next();
                this.sumNano = this.sumNano.add(new BigDecimal(adr.getBalance().toString()));
            }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void setSumBaseCurreny(){

            Iterator<NanoAddress> itr = this.nano_address_list.iterator();
            while (itr.hasNext()) {
                NanoAddress address = itr.next();
                this.sumBaseCurreny = this.sumBaseCurreny.add(address.getPrice());
                this.sumBaseCurreny = this.sumBaseCurreny.setScale(2, RoundingMode.DOWN);
            }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /***********************************************************************************************
     * private double getRateFromApi - GET rate from API
     * ********************************************************************************************/
    private double getRateFromApi(String base_currency){

        double rate=-1;

        try{
            String json_object = getHTML("https://min-api.cryptocompare.com/data/price?fsym=NANO&tsyms="+base_currency);
            JSONObject obj = new JSONObject(json_object);
            rate = (double) obj.get(base_currency);
        }catch (Exception e){
            Log.e("getRateFromApi", e.toString());
            rate = -2;
        }

        return rate;

    }

    /***********************************************************************************************
     * private static getHTML - Grabs HTML content of the page
     * ********************************************************************************************/
    private static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
