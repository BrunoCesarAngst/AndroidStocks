package com.example.myhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StocksHttp {

    public static String URL="https://api.hgbrasil.com/finance/";

    private  static HttpURLConnection conectar(String urlWeb){
        final  int SEGUNDOS =10000;
        try{
            java.net.URL url = new URL(urlWeb);
            HttpURLConnection conexao= (HttpURLConnection) url.openConnection();
            conexao.setReadTimeout(10*10000);
            conexao.setConnectTimeout(15*10000);
            conexao.setRequestMethod("GET");
            conexao.setDoInput(true);
            conexao.setDoOutput(false);
            conexao.connect();
            return  conexao;
        } catch (Exception e) {
            Log.d("URL ERROR", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static boolean hasConexao(Context context){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info= cm.getActiveNetworkInfo();
        return (info!=null &&info.isConnected());
    }

    public static ArrayList<Stocks> loadStocks(){
        try {
            HttpURLConnection connection= conectar(URL);
            int response=connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
         //       InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                JSONObject jsonObject = new JSONObject(bytesPraString(inputStream));
                //chegou JSON
                ArrayList<Stocks> stocks= readJsonCurrencie(jsonObject);
                return stocks;
            }
        } catch (IOException | JSONException e) {

            Log.d("JSON", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public  static ArrayList<Stocks> readJsonCurrencie(JSONObject json){
        ArrayList<Stocks> array = new ArrayList<>();
        try {
            JSONObject results= json.getJSONObject("results");
            JSONObject jsonCurrencies= results.getJSONObject("stocks");
            JSONObject ibovespa=jsonCurrencies.getJSONObject("IBOVESPA");
            JSONObject nasdaq=jsonCurrencies.getJSONObject("NASDAQ");
            JSONObject cac=jsonCurrencies.getJSONObject("CAC");
            JSONObject nikkei=jsonCurrencies.getJSONObject("NIKKEI");
            array.add(getStocksFromJson(ibovespa));
            array.add(getStocksFromJson(nasdaq));
            array.add(getStocksFromJson(cac));
            array.add(getStocksFromJson(nikkei));


        }catch (JSONException e){
            Log.d("JSON", e.getMessage());
        }
        return array;
    }


    public static Stocks getStocksFromJson(JSONObject json){

        String name;
        String location;
        Double points;
        Double variation;
        Stocks stocks = null;
        try {
            name = json.getString("name");
            location = json.getString("location");
            points = json.getDouble("points");
            variation=json.getDouble("variation");
            stocks = new Stocks(name, location, points, variation);
        }catch (JSONException e){
            Log.e("Log", e.getMessage());
        }
        return stocks;

    }

    private static String bytesPraString(InputStream inputStream){
        byte[] buffer= new byte[1024];
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        int bytesLidos;
        try {
            while ((bytesLidos=inputStream.read(buffer))!=-1){
                dados.write(buffer,0,bytesLidos);
            }
            return new String(dados.toByteArray(),"UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
