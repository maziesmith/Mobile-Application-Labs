package com.spencer.wille.politicalcentral;

/**
 * Created by wille on 6/20/2017.
 */

public class Bill {
    private String category, chamber, date, question, result, minus, plus, other, status;

    public Bill(String cat, String cham, String d, String quest, String res, String mi, String pl, String oth, String stat){
        category = cat;
        chamber = cham;
        date = d;
        question = quest;
        result = res;
        minus = mi;
        plus = pl;
        other = oth;
        status = stat;

    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMinu() {
        return minus;
    }

    public void setMinu(String minu) {
        minus = minu;
    }

    public String getPlu() {
        return plus;
    }

    public void setPlu(String plu) {
        plus = plu;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getStatu() {
        return status;
    }

    public void setStatu(String statu) {
        status = statu;
    }
}
