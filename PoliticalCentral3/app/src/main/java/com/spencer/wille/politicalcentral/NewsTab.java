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
import android.widget.CheckBox;
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

public class NewsTab extends Fragment {
    ArrayList<Article> articles;
    ArrayList<String> imgList;
    Button button;
    CheckBox cnnBox, waBox, wallBox, newBox;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setContentView(R.layout.activity_news);
        View v = getView();

        button = (Button) getActivity().findViewById(R.id.sourceButton);
        cnnBox = (CheckBox) getActivity().findViewById(R.id.checkbox_cnn);
        waBox = (CheckBox) getActivity().findViewById(R.id.checkbox_wapo);
        wallBox = (CheckBox) getActivity().findViewById(R.id.checkbox_wall);
        newBox = (CheckBox) getActivity().findViewById(R.id.checkbox_new);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                articles = new ArrayList<>();
                imgList = new ArrayList<>();
                ArrayList<String> sources = new ArrayList<>();
                if(waBox.isChecked()){
                    sources.add("the-washington-post");
                }
                if(cnnBox.isChecked()){
                    sources.add("cnn");
                }
                if(wallBox.isChecked()){
                    sources.add("the-wall-street-journal");
                }if(newBox.isChecked()){
                    sources.add("the-new-york-times");
                }

                Log.d("sources", sources.toString());
                // Perform action on click
                for(int x = 0; x < sources.size(); x++)
                {
                    new RetrieveFeedTask(getActivity(),articles, sources.get(x), imgList).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //Calls api
                };
            }
        });


    }

}
class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
    private String mSource;
    private String mKey;
    private TextView responseView;
    RecyclerView rvArticles;
    private Context mContext;
    public View rootView;
    ArrayList<Article> articles;
    ArrayList<String> imgList;
    public RetrieveFeedTask(Context a, ArrayList<Article> art, String source, ArrayList<String> iL) {
        mContext = a;
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvArticles = (RecyclerView) rootView.findViewById(R.id.rvArticles);
        articles = art;
        mSource = source;
        imgList = iL;
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            //mSource = "CNN";
            mKey = "84fec8f09f6a485aaa3a084b4c368cb4";
            /*String call = "https://newsapi.org/v1/articles?source=" + mSource + "&sortBy=top&apiKey=" + mKey;*/
            /*URL url = new URL(call);*/
            URL url = new URL("https://newsapi.org/v1/articles?source=" + mSource + "&sortBy=top&apiKey=84fec8f09f6a485aaa3a084b4c368cb4");
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
            JSONArray jsonArray = jsonObj.getJSONArray("articles");
            for(int x = 0; x < jsonArray.length(); x++){
                JSONObject jSonObj2 = jsonArray.getJSONObject(x);

                Article article = new Article(jSonObj2.getString("title"), jSonObj2.getString("description"), mSource, null, jSonObj2.getString("url"));
                articles.add(article);
                imgList.add(jSonObj2.getString("urlToImage"));

            }
            Log.d("Lengths", articles.size() + "" + imgList.size());
            new RetrievePictureTask(mContext, articles, imgList).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            ArticleAdapter adapter = new ArticleAdapter(mContext, articles);
            // Attach the adapter to the recyclerview to populate items
            rvArticles.setAdapter(adapter);
            // Set layout manager to position the items
            rvArticles.setLayoutManager(new LinearLayoutManager(mContext));

        }
        catch(JSONException e){
            Log.d("error2", e.toString());
        }

    }
}

class RetrievePictureTask extends AsyncTask<Void, Void, String> {
    private String mSource;
    private String mKey;
    private TextView responseView;
    RecyclerView rvArticles;
    private Context mContext;
    public View rootView;
    ArrayList<Article> articles;
    ArrayList<String> urlImgs;
    public RetrievePictureTask(Context a, ArrayList<Article> art, ArrayList<String> imgURLs) {
        mContext = a;
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvArticles = (RecyclerView) rootView.findViewById(R.id.rvArticles);
        articles = art;
        urlImgs = imgURLs;
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here
        try {
            for(int x = 0; x < urlImgs.size(); x++){
                InputStream is = (InputStream) new URL(urlImgs.get(x)).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                articles.get(x).setImage(d);
            }

        } catch (Exception e) {
            Log.d("imageEx", e.toString());
            return null;
        }
        return null;
    }

    protected void onPostExecute(String response) {
        ArticleAdapter adapter = new ArticleAdapter(mContext, articles);
            // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(adapter);
            // Set layout manager to position the items
        rvArticles.setLayoutManager(new LinearLayoutManager(mContext));

        articles = null;

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.d("imageEx", e.toString());
            return null;
        }
    }
}


