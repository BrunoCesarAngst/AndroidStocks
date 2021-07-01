package com.example.myhttp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StocksAdapter extends ArrayAdapter<Stocks> {

    public StocksAdapter(@NonNull Context context, @NonNull List<Stocks> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stocks stocks= getItem(position);
        View listaStocks = convertView;
        if(convertView==null){
            listaStocks= LayoutInflater.from(getContext()).inflate(R.layout.stocks_item,null);
        }
        TextView name = listaStocks.findViewById(R.id.txtStock);
        TextView txtLocal = listaStocks.findViewById(R.id.txtLocal);
        TextView txtPoint = listaStocks.findViewById(R.id.txtPoint);
        TextView stockVariacao = listaStocks.findViewById(R.id.txtStockVariacao);
            name.setText(stocks.getName());
            txtLocal.setText(stocks.getLocation());
            txtPoint.setText(stocks.getPoints());
            stockVariacao.setText(stocks.getVariation());


        return listaStocks;
    }
}
