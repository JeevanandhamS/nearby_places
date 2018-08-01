package com.jeeva.sms.data.webservice.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NearByPlacesResponse implements Serializable {

    @SerializedName("results")
    @Expose
    private List<Place> results = null;

    public List<Place> getResults() {
        return results;
    }

    public void setResults(List<Place> results) {
        this.results = results;
    }

}