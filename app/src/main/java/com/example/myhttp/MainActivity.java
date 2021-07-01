package com.example.myhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Currencies> mCurrencies;
    ArrayList<Stocks> mStocks;
    ArrayAdapter<Currencies> mAdapter;
    ArrayAdapter<Stocks> mStocksAdapter;
    ListView mList;
    ListView mList2;
    CurrenciesTask mTask;
    StocksTask mStocksTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = findViewById(R.id.Lista);
        mList2 = findViewById(R.id.Lista2);

        buscar();
        buscar2();
    }
    private void buscar(){
        if(mCurrencies==null){
            mCurrencies= new ArrayList<Currencies>();
        }

        mAdapter= new CurrenciesAdapter(getApplicationContext(),mCurrencies);
        mList.setAdapter(mAdapter);

        if(mTask==null){
            if (CurrenciesHttp.hasConexao(this)){
                start();
            }else{
                Toast.makeText(getApplicationContext(),"Erro a buscar....",Toast.LENGTH_SHORT).show();
            }
        }else if(mTask.getStatus()==AsyncTask.Status.RUNNING){
            Toast.makeText(getApplicationContext(),"....",Toast.LENGTH_SHORT).show();
        }
    }

    private void buscar2(){

        if(mStocks==null){
            mStocks= new ArrayList<Stocks>();
        }
        mStocksAdapter= new StocksAdapter(getApplicationContext(),mStocks);
        mList2.setAdapter(mStocksAdapter);

        if(mStocksTask==null){
            if (CurrenciesHttp.hasConexao(this)){
                start2();
            }else{
                Toast.makeText(getApplicationContext(),"Erro a buscar....",Toast.LENGTH_SHORT).show();
            }
        }else if(mStocksTask.getStatus()==AsyncTask.Status.RUNNING){
            Toast.makeText(getApplicationContext(),"....",Toast.LENGTH_SHORT).show();
        }

    }



    public void start(){
        if(mTask ==null || mTask.getStatus()!= AsyncTask.Status.RUNNING){
            mTask = new CurrenciesTask();
            mTask.execute();
        }
    }

    public void start2(){
        if(mStocksTask ==null || mStocksTask.getStatus()!= AsyncTask.Status.RUNNING){
            mStocksTask = new StocksTask();
            mStocksTask.execute();
        }
    }

    class CurrenciesTask extends AsyncTask<Void,Void, ArrayList<Currencies>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"Pronto....",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Currencies> doInBackground(Void... voids) {
            ArrayList<Currencies> coinsList= CurrenciesHttp.loadCurrencies();
            return coinsList;
        }

        @Override
        protected void onPostExecute(ArrayList<Currencies> currencies) {
            super.onPostExecute(currencies);
            if (currencies!=null){
                mCurrencies.clear();
                mCurrencies.addAll(currencies);
                mAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(),"Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }

    class StocksTask extends AsyncTask<Void,Void, ArrayList<Stocks>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(),"Pronto....",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Stocks> doInBackground(Void... voids) {
            ArrayList<Stocks> stocksList= StocksHttp.loadStocks();
            return stocksList;
        }

        @Override
        protected void onPostExecute(ArrayList<Stocks> stocks) {
            super.onPostExecute(stocks);
            if (stocks!=null){
                mStocks.clear();
                mStocks.addAll(stocks);
                mStocksAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getApplicationContext(),"Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}