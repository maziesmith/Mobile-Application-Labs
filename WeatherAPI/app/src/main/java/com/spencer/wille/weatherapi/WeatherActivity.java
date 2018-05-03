package com.spencer.wille.weatherapi;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class WeatherActivity extends AppCompatActivity {

    private AsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        TextView responseView = (TextView) findViewById(R.id.textViewID);
        EditText input = (EditText) findViewById(R.id.editView);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                callAPI();

            }
        });

    }
    public void callAPI(){
        /*myAsyncTask = new RetrieveFeedTask(this);
        myAsyncTask.execute();*/
        new RetrieveFeedTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

}
class RetrieveFeedTask extends AsyncTask<Void, Void, String>{
    String API_URL = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=";

    public Context mContext;
    public View rootView;
    private Exception exception;
    private TextView responseView;
    private EditText input;
    private String cityName;
    public RetrieveFeedTask(Context a) {
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        responseView = (TextView)rootView.findViewById(R.id.textViewID);
        input = (EditText) rootView.findViewById(R.id.editView);
        cityName = input.getText().toString();
    }



    protected void onPreExecute() {
        responseView.setText("");
    }

    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+ cityName + "&APPID=b86bea000ba12922cc9d5ffd68f0147c");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "Try Another City";
        }
        try{
            JSONObject jsonObj = new JSONObject(response);
            JSONObject jSonObj2 = jsonObj.getJSONObject("main");
            String vis = jsonObj.getString("visibility");
            String temp = jSonObj2.getString("temp");
            temp = Double.parseDouble(temp) * (9.0/5.0) - 459.67 + "";
            String min = jSonObj2.getString("temp_min");
            min = Double.parseDouble(min) * (9.0/5.0) - 459.67 + "";
            String max = jSonObj2.getString("temp_max");
            max = Double.parseDouble(max) * (9.0/5.0) - 459.67 + "";
            String hum = jSonObj2.getString("humidity");
            responseView.setText("Temperature: " + temp + "\nMax Temperature: " + max + "\nMin Temperature: " + min +  "\nVisibility: " + vis + "\nHumidity: " + hum);
            //responseView.setText(+ hum);
        }
        catch(JSONException e){
            responseView.setText(response);
        }

    }
}