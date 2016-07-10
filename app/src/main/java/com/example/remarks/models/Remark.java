package com.example.remarks.models;

import com.example.remarks.interfaces.TextInterface;

public class Remark implements TextInterface {
    protected String message;
    public long timestamp;

    @Override
    public String getText() {
        return message;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}
