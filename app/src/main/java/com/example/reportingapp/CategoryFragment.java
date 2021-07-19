package com.example.reportingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.reportingapp.models.Category;
import com.example.reportingapp.utility.CategoryAdapter;

import java.util.List;

public class CategoryFragment extends Fragment {
        private ListView mCategoryListView;

        CategoryAdapter mCategoryAdapter;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        mCategoryListView = view.findViewById(R.id.category_list_view);
        final List<String> categoryList = Category.getListInstance();
        mCategoryAdapter = new CategoryAdapter(getActivity(),R.layout.item_category_list,categoryList);
        mCategoryListView.setAdapter(mCategoryAdapter);
        mCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewCategoryActivity.class);
                intent.putExtra("ViewCategoryActivity", categoryList.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

        }

