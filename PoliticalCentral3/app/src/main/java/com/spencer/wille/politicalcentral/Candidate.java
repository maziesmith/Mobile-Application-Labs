package com.spencer.wille.politicalcentral;

/**
 * Created by wille on 5/31/2017.
 */

public class Candidate {
    private String name;
    private String party;
    private String url;
    private String phone;
    private String email;

    public Candidate(String n, String pa, String u, String po, String e){
        name = n;
        party = pa;
        url = u;
        phone = po;
        email = e;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
