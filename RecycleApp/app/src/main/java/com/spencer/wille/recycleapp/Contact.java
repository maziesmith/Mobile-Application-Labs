package com.spencer.wille.recycleapp;

import java.util.ArrayList;

/**
 * Created by wille on 4/26/2017.
 */

public class Contact{
    private String mName;
    private String mMessage;

    public Contact(String name, String message) {
        mName = name;
        mMessage = message;
    }

    public String getName() {
        return mName;
    }

    public String getMessage() {
        return mMessage;
    }

}
