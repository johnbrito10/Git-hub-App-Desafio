package com.example.professor.githubpesquisalistview;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int total_count;

    private boolean incomplete_results;


    private List<Items> items = new ArrayList<>();

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}

