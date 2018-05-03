package com.spencer.wille.politicalcentral;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by wille on 6/14/2017.
 */

public class Contest {
    private String type;
    private String office;
    private ArrayList<Candidate> candidateList;

    public Contest(String t, String o, ArrayList<Candidate> can){
        type = t;
        office = o;
        candidateList = can;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public ArrayList<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(ArrayList<Candidate> candidateList) {
        this.candidateList = candidateList;
    }
}
