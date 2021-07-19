package com.example.reportingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.reportingapp.models.Report;
import com.example.reportingapp.utility.ViewCategoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewCategoryActivity extends AppCompatActivity {
    //widgets
    private ListView mListView;
    private ProgressBar mProgressBar;
    //vars
    private ArrayList<Report> mReports;
    private ValueEventListener mValueEventListener;
    private String mCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);
        mListView = findViewById(R.id.view_category_list_view);
        mProgressBar = findViewById(R.id.progressBar5);
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getAllReports();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
            };
        init();
    }

    private void init() {
      enableReportListener();
    }
    private void enableReportListener(){
        Query mQuery = FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_reports))
                .orderByChild("category_name")
                .equalTo(mCategoryName);

        mQuery.addValueEventListener(mValueEventListener);
    }

    private void getAllReports() {
        showProgressBar();
        mCategoryName = "";

        Intent intent = getIntent();
        if(intent.hasExtra("ViewCategoryActivity")){
            String category_name = intent.getStringExtra("ViewCategoryActivity");
            mCategoryName = category_name;
        }
        mReports = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbnode_reports))
                .orderByChild("category_name")
                .equalTo(mCategoryName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot:  dataSnapshot.getChildren()){
                    Report report = new Report();
                    report.setReport(singleSnapshot.getValue(Report.class).getReport());
                    report.setCategory_name(singleSnapshot.getValue(Report.class).getCategory_name());
                    report.setReport_id(singleSnapshot.getValue(Report.class).getReport_id());
                    report.setUser_id(singleSnapshot.getValue(Report.class).getUser_id());
                    mReports.add(report);
                }

                setupReportList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupReportList() {
        ViewCategoryAdapter adapter = new ViewCategoryAdapter(this, R.layout.item_report_list, mReports);
        mListView.setAdapter(adapter);
        hideProgressBar();
    }
    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}