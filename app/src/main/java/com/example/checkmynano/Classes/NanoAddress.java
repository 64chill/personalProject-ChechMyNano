package com.example.checkmynano.Classes;
/***
 * public class NanoAddress
 *  public                NanoAddress(String address, double conversationRate, Context context )
 *  public   String       getAddress()
 *  public   BigDecimal   getBalance()
 *  public   BigDecimal   getPrice()
 *  private  void         setBalance()
 *  private  void         setPrice()
 *
 *
 *
 *
 *
 *
 ***/

import android.content.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.math.BigDecimal;
import java.math.RoundingMode;



public class NanoAddress {
    //BgDecimal - in case of large number of nano - baseCurrency
    private String address;
    private BigDecimal balance = BigDecimal.valueOf(0.0); //balance in Nano, default is 0
    private BigDecimal price = BigDecimal.valueOf(0.0); //price in against Base Currency
    private double conversationRate;
    private Context context;



    /** Constructor *******************************************************************************/
    public NanoAddress(String address, double conversationRate, Context context ) {
        this.address = address;
        this.conversationRate = conversationRate;
        setBalance();
        setPrice();
        this.context = context;

    }

    /***Getters and Setters ***********************************************************************/

    public String getAddress() {
        return address;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /***********************************************************************************************
     * Scrape balance from : https://nanoexplorer.io/accounts/
     * Sum unpocketed + pocketed
     * return balance in Nano
     * *********************************************************************************************/

    private void setBalance() {
        Document doc;
        String nanoExplorer = "https://nanoexplorer.io/accounts/";
        nanoExplorer+=this.address;
        try{
            doc = Jsoup.connect(nanoExplorer).timeout(6000).get();
            Elements elements = doc.select("tr.account-header-detail p.medium-value");
            int counter=0;
            // sum of balance + pending balance
            // 0 - nano balance
            // 2 - pending nano balance
            for (Element element : elements) {
                if(counter == 0 || counter==2){
                    this.balance = this.balance.add(new BigDecimal(element.text().replace(",","")));
                    this.balance = this.balance.setScale(2, RoundingMode.DOWN);
                }
                counter++;
            }
        } catch(Exception e){
            this.balance.equals("-1");
        }
    }


    private void setPrice() {
        BigDecimal b = BigDecimal.valueOf(0.0);
        BigDecimal bb = BigDecimal.valueOf(0.00);

        if(this.conversationRate>=0 && !(this.balance.equals(b) && !(this.balance.equals(bb)) )){
            this.price = this.balance.multiply(new BigDecimal(this.conversationRate));
            this.price = this.price.setScale(2, RoundingMode.DOWN);
        }
    }

}

