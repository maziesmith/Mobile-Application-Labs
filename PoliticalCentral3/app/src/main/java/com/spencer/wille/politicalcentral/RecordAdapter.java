package com.spencer.wille.politicalcentral;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wille on 6/20/2017.
 */

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class RecordAdapter extends
        RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView catTextView;
        public TextView questTextView;
        public TextView chamTextView;
        public TextView resTextView;
        public TextView supTextView;
        public TextView dateTextView;
        public TextView statTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            catTextView = (TextView) itemView.findViewById(R.id.category);
            chamTextView = (TextView) itemView.findViewById(R.id.chamber);
            questTextView = (TextView) itemView.findViewById(R.id.question);
            resTextView = (TextView) itemView.findViewById(R.id.result);
            supTextView = (TextView) itemView.findViewById(R.id.support);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            statTextView = (TextView) itemView.findViewById(R.id.status);
        }
    }
    // Store a member variable for the contacts

    private List<Bill> mBills;
    // Store the context for easy access

    private Context mContext;

    // Pass in the contact array into the constructor

    public RecordAdapter(Context context, List<Bill> bills) {
        mBills = bills;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview

    private Context getContext() {
        return mContext;

    }
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_record, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Bill bill = mBills.get(position);
        // Set item views based on your views and data model
        TextView catView = viewHolder.catTextView;
        TextView dateView = viewHolder.dateTextView;
        TextView supView = viewHolder.supTextView;
        TextView resView = viewHolder.resTextView;
        TextView questView = viewHolder.questTextView;
        TextView chamView = viewHolder.chamTextView;
        TextView statView = viewHolder.statTextView;

        catView.setText("Category: " + bill.getCategory());
        dateView.setText("Date: " + bill.getDate());
        chamView.setText("Chamber: " + bill.getChamber());
        resView.setText("Result: " + bill.getResult());
        questView.setText(bill.getQuestion());
        //statView.setText("Current Status: " +  bill.getStatu());

        String support = "Vote:" + "\t" + "Yay: " + bill.getPlu() + " Nay: " + bill.getMinu() + " Other: " + bill.getOther();
        supView.setText(support);



    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBills.size();
    }

}
