package com.spencer.wille.politicalcentral;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ArrayList<Article> articles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        articles = new ArrayList<>();

        new RetrieveFeedTask(this,articles).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //Calls api

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
    public RetrieveFeedTask(Context a, ArrayList<Article> art) {
        mContext = a;
        rootView = ((Activity)a).getWindow().getDecorView().findViewById((android.R.id.content));
        rvArticles = (RecyclerView) rootView.findViewById(R.id.rvArticles);
        articles = art;
    }


    protected String doInBackground(Void... urls) {
        // Do some validation here

        try {
            mSource = "CNN";
            mKey = "84fec8f09f6a485aaa3a084b4c368cb4";
            /*String call = "https://newsapi.org/v1/articles?source=" + mSource + "&sortBy=top&apiKey=" + mKey;*/
            /*URL url = new URL(call);*/
            URL url = new URL("https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=84fec8f09f6a485aaa3a084b4c368cb4");
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
                Drawable image = LoadImageFromWebOperations(jSonObj2.getString("urlToImage"));
                Article article = new Article(jSonObj2.getString("title"), jSonObj2.getString("description"), mSource, image, jSonObj2.getString("url"));
                articles.add(article);
            }

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
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            Log.d("gotHere", url);
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.d("imageEx", e.toString());
            return null;
        }
    }
}
