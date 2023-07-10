package com.example.nodojs_api.model;

import java.util.List;

public class User {
    private boolean success;
    private List<Data> data;

    public User() {
    }

    public User(boolean success, List<Data> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
