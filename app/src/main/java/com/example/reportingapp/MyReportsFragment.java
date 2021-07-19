package com.example.reportingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.reportingapp.models.Report;
import com.example.reportingapp.utility.MyReportsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyReportsFragment extends Fragment {

    //widgets
    private ListView mListView;
    private ProgressBar mProgressBar;
    //vars
    private ArrayList<Report> mReports;
    private MyReportsAdapter mAdapter;
    private ValueEventListener mMValueEventListener;


    private void init() {
        enableReportListener();

    }

    private void enableReportListener() {
       Query mReportReference = FirebaseDatabase.getInstance().getReference().child(getString(R.string.dbnode_reports))
               .orderByChild("user_id")
               .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mReportReference.addValueEventListener(mMValueEventListener);
    }

    private void getAllReports() {
        showProgressBar();
        mReports = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(getString(R.string.dbnode_reports))
                .orderByChild("user_id")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
        mAdapter = new MyReportsAdapter(getActivity(),R.layout.item_report_list,mReports);
        mListView.setAdapter(mAdapter);
        hideProgressBar();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_reports, container, false);
        mProgressBar = view.findViewById(R.id.progressBar4);
        mListView = view.findViewById(R.id.my_report_list_view);
        mMValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               getAllReports();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        init();
        return view;
    }
    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}