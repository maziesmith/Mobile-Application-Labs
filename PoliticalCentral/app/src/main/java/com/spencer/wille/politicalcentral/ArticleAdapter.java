package com.spencer.wille.politicalcentral;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by wille on 5/19/2017.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ArticleAdapter extends
            RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView nameTextView;
        public ImageView imTextView;
        public TextView descTextView;
        public TextView sourceTextView;
        public TextView urlTextView;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.article_name);
            imTextView = (ImageView) itemView.findViewById(R.id.image_name);
            descTextView = (TextView) itemView.findViewById(R.id.desc_name);
            sourceTextView = (TextView) itemView.findViewById(R.id.source_name);
            urlTextView = (TextView) itemView.findViewById(R.id.url_name);
        }
    }
        // Store a member variable for the contacts

    private List<Article> mArticles;
        // Store the context for easy access

    private Context mContext;

        // Pass in the contact array into the constructor

    public ArticleAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

        // Easy access to the context object in the recyclerview

    private Context getContext() {
        return mContext;

    }
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_article, parent, false);
            // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

        // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);
            // Set item views based on your views and data model
        TextView nameView = viewHolder.nameTextView;
        ImageView imageView = viewHolder.imTextView;
        TextView descView = viewHolder.descTextView;
        TextView sourceView = viewHolder.sourceTextView;
        TextView urlView = viewHolder.urlTextView;
        nameView.setText(article.getName());
        descView.setText(article.getDescription());
        sourceView.setText("Source: " + article.getSource());
        imageView.setImageDrawable(article.getImage());
        urlView.setText(article.getUrl());
        Linkify.addLinks(urlView, Linkify.WEB_URLS);


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
            return mArticles.size();
        }

}
