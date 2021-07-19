package com.example.reportingapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import com.example.reportingapp.models.Category;
import com.example.reportingapp.models.Report;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddReportDialog extends DialogFragment {

    private Spinner mSpinner;
    private EditText mEditText;
    private AppCompatButton mButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_report, container, false);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        mEditText = (EditText) view.findViewById(R.id.report_text);
        mButton = (AppCompatButton) view.findViewById(R.id.btn_add_report);
        setUpSpinner();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mEditText.getText().toString().equals("")){
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    String reportId = reference
                            .child(getString(R.string.dbnode_reports))
                            .push().getKey();
                    //create the report
                    Report report = new Report();
                    report.setCategory_name((String) mSpinner.getSelectedItem());
                    report.setReport(mEditText.getText().toString());
                    report.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    report.setReport_id(reportId);
                    //insert the new report into the database
                    reference
                            .child(getString(R.string.dbnode_reports))
                            .child(reportId)
                            .setValue(report);
                    getDialog().dismiss();
                }
            }
        });


        return view;
    }

    private void setUpSpinner() {
        List<String> categories = Category.getListInstance();
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapterCategory);
    }



}

















