package com.spencer.wille.politicalcentral;


/**
 * Created by wille on 5/31/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import static android.R.attr.x;


public class RecordTab extends Fragment {
    ArrayList<Bill> bills;
    Button button;
    EditText editState, editDis;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.record, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setContentView(R.layout.activity_news);



        View v = getView();
        bills = new ArrayList<Bill>();
        new RetrieveFeedTaskBills(getActivity(), bills).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        /*button = (Button) getActivity().findViewById(R.id.recbuttonID);
        *//*editState = (EditText) getActivity().findViewById(R.id.stateID);
        editDis = (EditText) getActivity().findViewById(R.id.disID);*//*
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               *//* String district = editDis.getText().toString();
                String state = editState.getText().toString();*//*
                // Perform action on click
                new RetrieveFeedTaskBills(getActivity(), bills).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });*/


    }

}
class RetrieveFeedTaskBills extends AsyncTask<Void, Void, String> {
    private RecyclerView rvRecord;
    private Context mContext;
    public View rootView;
    ArrayList<Bill> bills;
    public RetrieveFeedTaskBills(Context a, ArrayList<Bill> bil) {
        mContext = a;
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvRecord = (RecyclerView) rootView.findViewById(R.id.rvRecord);
        bills = bil;
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            URL url = new URL("https://www.govtrack.us/api/v2/vote?sort=-created");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();

            urlConnection.disconnect();
            return stringBuilder.toString();
        }

        catch(Exception e) {
            Log.d("error", e.toString());
            return null;
        }
    }

    protected void onPostExecute(String response) {
        if(response == null) {
            response = "Failed";
        }
        try{
            JSONObject jsonObj = new JSONObject(response);
            JSONArray jsonRay = jsonObj.getJSONArray("objects");
            for(int x = 0; x < jsonRay.length(); x++){
                JSONObject bill = jsonRay.getJSONObject(x);
                String category = bill.getString("category_label");
                String chamber = bill.getString("chamber_label");
                String date = bill.getString("created");
                String question = bill.getString("question");
                String result = bill.getString("result");
                String minus = bill.getString("total_minus");
                String plus = bill.getString("total_plus");
                String other = bill.getString("total_other");

                String status = "";
                if(bill.isNull("current_status_description")){
                    status = "None";
                }
                else{
                    status = bill.getString("current_status_description");
                }


                bills.add(new Bill(category, chamber, date, question, result, minus, plus, other, status));
            }

            RecordAdapter adapter = new RecordAdapter(mContext, bills);
            // Attach the adapter to the recyclerview to populate items
            rvRecord.setAdapter(adapter);
            // Set layout manager to position the items
            rvRecord.setLayoutManager(new LinearLayoutManager(mContext));
        }
        catch(JSONException e){
            Log.d("error2", e.toString());
        }

    }
}
/*
class RetrieveFeedTaskRecord extends AsyncTask<Void, Void, String>{
    private String mKey, mState, mDis, mChamber;
    private TextView responseView;
    RecyclerView rvLegislators;
    private Context mContext;
    public View rootView;
    ArrayList<Election> legislators;

    public RetrieveFeedTaskRecord(Context a, ArrayList<Election> el, String state, String dis) {
        mContext = a;
        rootView = ((Activity) a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvLegislators = (RecyclerView) rootView.findViewById(R.id.rvLegislators);
        legislators = el;
        mState = state;
        mDis = dis;
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here
        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = null;

        try {
            Log.d("started", "start");
            mKey = "M2VyoXcvuX1fBBu5AHxI5ROvYhG6Wjs9BwmPSe88";
            mChamber = "house"; //I DO NOT KNOW HOW TO CHANGE THIS PROGROMATICALLY!
            URI url = new URI("http://api.propublica.org/congress/v1/members/" + mChamber + "/" + mState + "/" + mDis + "/current.json");

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(url);

            List<NameValuePair> params = new LinkedList<NameValuePair>();
            params.add(new BasicNameValuePair("X-API-Key", mKey));

            HttpResponse httpResponse = httpClient.execute(httpGet);
            InputStream inputStream = httpResponse.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String readLine = bufferedReader.readLine();
            while(readLine != null){
                stringBuffer.append(readLine);
                stringBuffer.append("\n");
                readLine = bufferedReader.readLine();
            }

*/
/*
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //urlConnection.setRequestProperty("Content-Type", "application/json");
            Log.d("gothere", "k");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("X-API-Key", mKey);
//curl "https://api.propublica.org/congress/v1/members/senate/RI/current.json"
            //-H "X-API-Key: PROPUBLICA_API_KEY"

            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            Log.d("success", inputStreamReader.toString());

            return "ss";*//*

        }
        catch (Exception e) {
            Log.d("recErr", e.toString());
        }
        finally{
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }
                catch (IOException e){
                    Log.d("IOEx", e.toString());
                }
            }
        }
        return stringBuffer.toString();
    }
    protected void onPostExecute(String response) {

        try {
            if(response != null){
                Log.d("SUCCESS", response.toString());
                JSONObject obj = new JSONObject(response);
            }


        } catch (JSONException e) {
            Log.d("error2", e.toString());
        }
    }
}*/
