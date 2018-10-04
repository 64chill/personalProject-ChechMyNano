package com.example.checkmynano.addrListAdapter;
/***
 * public class nanoAddressListAdapter extends ArrayAdapter<NanoAddress>
 *      public      nanoAddressListAdapter   (Context context, int textViewResourceId)
 *      public      nanoAddressListAdapter   (Context context, int resource, List<NanoAddress> items, String basecurrency)
 *      public View getView                  (int position, View convertView, ViewGroup parent)  - @Override
 *
 *
 *
 *
 *
 ***/


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.checkmynano.Classes.NanoAddress;
import com.example.checkmynano.R;

import java.util.List;

public class nanoAddressListAdapter extends ArrayAdapter<NanoAddress> {
    String basecurrency;

        public nanoAddressListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public nanoAddressListAdapter(Context context, int resource, List<NanoAddress> items, String basecurrency) {
            super(context, resource, items);
            this.basecurrency = basecurrency;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.adapter_view_layout_main, null);
            }

            NanoAddress p = getItem(position);

            if (p != null) {
                TextView address = (TextView) v.findViewById(R.id.adapter_view_nanoAddr);
                TextView balance = (TextView) v.findViewById(R.id.adapter_view_nanoBalance);
                TextView price   = (TextView) v.findViewById(R.id.adapter_view_usdBalance);

                if (address != null) {
                    address.setText(p.getAddress());
                } else{
                    address.setText("err");
                }

                if (balance != null) {
                    balance.setText(String.valueOf("    "+p.getBalance())+" NANO");
                }else{
                    balance.setText("err NANO");
                }

                if (price != null) {
                    price.setText(String.valueOf("    "+p.getPrice())+" "+basecurrency);
                }else{
                    price.setText("err USD");
                }
            }

            return v;
        }

}



