package com.spencer.wille.politicalcentral;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wille on 5/31/2017.
 */

public class ElectionAdapter extends
        RecyclerView.Adapter<ElectionAdapter.ViewHolder> {

        // Provide a direct reference to each of the views within a data item
        // Used to cache the views within the item layout for fast access

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // Your holder should contain a member variable
            // for any view that will be set as you render a row

            public LinearLayout mLinLay;

            // We also create a constructor that accepts the entire item row
            // and does the view lookups to find each subview
            public ViewHolder(View itemView) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(itemView);

                mLinLay = (LinearLayout) itemView.findViewById(R.id.linlayID);
            }
        }
        // Store a member variable for the contacts

        private List<Election> mElections;
        // Store the context for easy access

        private Context mContext;

        // Pass in the contact array into the constructor

        public ElectionAdapter(Context context, List<Election> elections) {
            mElections = elections;
            mContext = context;
        }

        // Easy access to the context object in the recyclerview

    private Context getContext() {
        return mContext;

    }
    @Override
    public ElectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_election, parent, false);
        // Return a new holder instance
        ElectionAdapter.ViewHolder viewHolder = new ElectionAdapter.ViewHolder(articleView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ElectionAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Election election = mElections.get(position);
        // Set item views based on your views and data model

        LinearLayout linearLayout = viewHolder.mLinLay;

        TextView textViewName = new TextView(mContext);
        textViewName.setText(election.getName());
        textViewName.setTextSize(22);
        textViewName.setTypeface(null, Typeface.BOLD);
        linearLayout.addView(textViewName);

        TextView textViewDate = new TextView(mContext);
        textViewDate.setText(election.getDate());
        linearLayout.addView(textViewDate);

        TextView textViewBlank  = new TextView(mContext); //blank
        textViewBlank.setText("");
        linearLayout.addView(textViewBlank);

        ArrayList<Contest> contests = election.getContestList();

        for(int con = 0; con < contests.size(); con++){
            Contest currCon = contests.get(con);

            TextView textViewType = new TextView(mContext);
            textViewType.setText(currCon.getType());
            textViewType.setTextSize(18);
            textViewType.setTypeface(null, Typeface.BOLD);
            linearLayout.addView(textViewType);

            TextView textViewOff = new TextView(mContext);
            textViewOff.setText(currCon.getOffice());
            linearLayout.addView(textViewOff);

            TextView textView6 = new TextView(mContext);
            textView6.setText("");
            linearLayout.addView(textView6);

            ArrayList<Candidate> candidates = currCon.getCandidateList();

            TextView textViewCan = new TextView(mContext);
            textViewCan.setText("Candidates");
            textViewCan.setTextSize(16);
            textViewCan.setTypeface(null, Typeface.ITALIC);
            linearLayout.addView(textViewCan);
            for(int can = 0; can < candidates.size(); can++){
                Candidate can1 = candidates.get(can);

                TextView textView1 = new TextView(mContext);
                textView1.setText(can1.getName());
                linearLayout.addView(textView1);

                TextView textView2 = new TextView(mContext);
                textView2.setText("Party: " + can1.getParty());
                linearLayout.addView(textView2);

                TextView textView3 = new TextView(mContext);
                textView3.setText("URL: " + can1.getUrl());
                Linkify.addLinks(textView3, Linkify.WEB_URLS);
                linearLayout.addView(textView3);

                TextView textView4 = new TextView(mContext);
                textView4.setText("Phone: " + can1.getPhone());
                linearLayout.addView(textView4);

                TextView textView5 = new TextView(mContext);
                textView5.setText("Email: " + can1.getEmail());
                linearLayout.addView(textView5);

                TextView textViewB = new TextView(mContext);
                textViewB.setText("");
                linearLayout.addView(textViewB);

            }

        }


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mElections.size();
    }
}
