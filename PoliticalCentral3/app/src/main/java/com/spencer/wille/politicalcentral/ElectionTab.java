package com.spencer.wille.politicalcentral;

/**
 * Created by wille on 5/31/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ElectionTab extends Fragment {
    ArrayList<Election> elections;
    Button button;
    EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.election, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setContentView(R.layout.activity_news);
        View v = getView();
        elections = new ArrayList<>();

        button = (Button) getActivity().findViewById(R.id.buttonID);
        editText = (EditText) getActivity().findViewById(R.id.editID);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String address = editText.getText().toString();
                // Perform action on click
                new RetrieveFeedTask2(getActivity(), elections, address).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //Calls api
            }
        });


    }
}
class RetrieveFeedTask2 extends AsyncTask<Void, Void, String> {
    private String mKey;
    private String mAddress;
    private TextView responseView;
    RecyclerView rvElections;
    private Context mContext;
    public View rootView;
    ArrayList<Election> elections;
    public RetrieveFeedTask2(Context a, ArrayList<Election> el, String address) {
        mContext = a;
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvElections = (RecyclerView) rootView.findViewById(R.id.rvElections);
        elections = el;
        mAddress = address;
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            String addressStr = "";
            String[] array = mAddress.split(" ");
            for(int x = 0; x < array.length - 1; x++){
                addressStr += array[x] + "%20";
            }
            addressStr += array[array.length-1];
            mAddress = addressStr;
            //mAddress = "2153%20Westglen%20Ct%20Vienna%20VA%2022182";
            mKey = "AIzaSyBQjPH2AYYEaDwFFzQ9E7oDRnIWvu8zmS4";
            URL url = new URL("https://www.googleapis.com/civicinfo/v2/voterinfo?key=" + mKey + "&address=" + mAddress);
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
            JSONObject jsonObj2 = jsonObj.getJSONObject("election");
            ArrayList<Integer> idList = new ArrayList<Integer>();
            idList.add(jsonObj2.getInt("id"));


            if(!jsonObj.isNull("otherElections")){
                JSONArray jsonArray = jsonObj.getJSONArray("otherElections");
                for (int x = 0; x < jsonArray.length(); x++){
                    idList.add(jsonArray.getJSONObject(x).getInt("id"));
                }
            }


            ArrayList<Integer> testidList = new ArrayList<Integer>(); // I HAVE A TEST IN HERE WHILE IT DOES NOT WORK
            testidList.add(2000);
            Log.d("test", testidList.toString());

            new RetrieveFeedTask3(mContext, testidList, mAddress).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //Calls api2

        }
        catch(JSONException e){
            Log.d("error2", e.toString());
        }

    }
}
class RetrieveFeedTask3 extends AsyncTask<Void, Void, String> {
    private String mAddress;
    private String mKey;
    private TextView responseView;
    RecyclerView rvElections;
    private Context mContext;
    public View rootView;
    private ArrayList<Integer> mIDList;
    private JSONObject globJsonObj;
    private ArrayList<String> outputs;
    public RetrieveFeedTask3(Context a, ArrayList<Integer> idList, String address) {
        mAddress = address;
        mContext = a;
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvElections = (RecyclerView) rootView.findViewById(R.id.rvElections);
        mIDList = idList;

        outputs = new ArrayList<String>();
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            //mAddress = "2153 Westglen Ct Vienna VA 22182";
            mKey = "AIzaSyBQjPH2AYYEaDwFFzQ9E7oDRnIWvu8zmS4";

            for(int x = 0; x < mIDList.size(); x++){
                URL url = new URL("https://www.googleapis.com/civicinfo/v2/voterinfo?key=" + mKey + "&address=" + mAddress + "&electionId=" + mIDList.get(x));
                Log.d("URL", url.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                outputs.add(stringBuilder.toString());

                bufferedReader.close();

                urlConnection.disconnect();

            }
            return null;
        }

        catch(Exception e) {
            Log.d("error", e.toString());
            return null;
        }
    }

    protected void onPostExecute(String response) {

        try{
            ArrayList<Election> electionList = new ArrayList<Election>();

            for(int el = 0; el < outputs.size(); el++){
                ArrayList<Contest> contestList = new ArrayList<Contest>(); //START HERE, CHANGE ELECTION TO CONTEST

                int mID = mIDList.get(el);
                globJsonObj = new JSONObject(outputs.get(el));

                JSONObject electionStuff = new JSONObject(globJsonObj.getString("election"));
                String elName = electionStuff.getString("name");
                String elDay = electionStuff.getString("electionDay");
                JSONArray contestArray = new JSONArray(globJsonObj.getString("contests"));

                for(int con = 0; con < contestArray.length(); con++){
                    JSONObject contest = contestArray.getJSONObject(con);
                    if(contest.getString("type").equals("Referendum") == false){
                        String type = contest.getString("type");

                        String office;
                        if(!contest.isNull( "office" )) {
                            office = contest.getString("office");
                        }
                        else{
                            office = "None";
                        }


                        JSONArray canList = contest.getJSONArray("candidates");

                        ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
                        for(int can = 0; can < canList.length(); can++){
                            JSONObject candidate = canList.getJSONObject(can);
                            String name = candidate.getString("name");
                            String party = candidate.getString("party");
                            String url, phone, email;
                            if(!candidate.isNull( "candidateUrl" )) {
                                url = candidate.getString("candidateUrl");
                            }
                            else{
                                url = "None";
                            }
                            if(!candidate.isNull( "phone" )) {
                                phone = candidate.getString("phone");
                            }
                            else{
                                phone = "None";
                            }
                            if(!candidate.isNull( "email" )) {
                                email = candidate.getString("email");
                            }
                            else{
                                email = "None";
                            }
                            candidateList.add(new Candidate(name, party, url, phone, email));
                        }
                        contestList.add(new Contest(office, type, candidateList));
                    }
                }
                electionList.add(new Election(elName, mID, elDay, contestList));
            }
            //AFTER ELECTIONS AND CANDIDATE LISTS CREATED

            ElectionAdapter adapter = new ElectionAdapter(mContext, electionList);
            // Attach the adapter to the recyclerview to populate items
            rvElections.setAdapter(adapter);
            // Set layout manager to position the items
            rvElections.setLayoutManager(new LinearLayoutManager(mContext));

        }
        catch(JSONException e){
            Log.d("error2", e.toString());
        }

    }
}



