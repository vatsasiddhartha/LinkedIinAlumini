package com.siddhartha.Alumini_Searcher.dto;

import com.siddhartha.Alumini_Searcher.Entity.Alumini;

import java.util.List;

public class PhantomResponse {

    private List<Alumini> data;

    public List<Alumini> getData() {
        return data;
    }

    public void setData(List<Alumini> data) {
        this.data = data;
    }
}
