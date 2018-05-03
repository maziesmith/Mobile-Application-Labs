package com.spencer.wille.politicalcentral;


import java.util.ArrayList;

/**
 * Created by wille on 5/31/2017.
 */

public class Election {
    private String name;
    private int id;
    private String date;
    private ArrayList<Contest> contestList;

    public Election(String n, int i, String d, ArrayList<Contest> c){
        name = n;
        id = i;
        date = d;
        contestList = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Contest> getContestList() {
        return contestList;
    }

    public void setContestList(ArrayList<Contest> contestList) {
        this.contestList = contestList;
    }
}
