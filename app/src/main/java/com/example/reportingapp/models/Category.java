package com.example.reportingapp.models;

import com.example.reportingapp.R;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private static final List<String> mCategories = new ArrayList<>();
    private static Category ourInstance  = null;
    public static List<String> getListInstance() {
        if(ourInstance == null) {
            ourInstance = new Category();
            ourInstance.populateList();
        }
        return mCategories;
    }
    private void populateList (){
    mCategories.add("Accident");
    mCategories.add("Fighting");
    mCategories.add("Rioting");
    mCategories.add("Kidnapping");
    }
}
