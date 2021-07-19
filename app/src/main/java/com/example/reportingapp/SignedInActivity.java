package com.example.reportingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.reportingapp.models.FragmentTag;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class SignedInActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationViewEx mBottomNavigationViewEx;
    private MainHomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private MyReportsFragment mMyReportsFragment;
    private ArrayList<String> mFragmentsTags = new ArrayList<>();
    private ArrayList<FragmentTag> mFragments = new ArrayList<>();
    private int mExitCount = 0;
    private FloatingActionButton fab;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        int backStackCount = mFragmentsTags.size();
        if(backStackCount > 1){
            String topFragmentTag = mFragmentsTags.get(backStackCount - 1);

            String newTopFragmentTag = mFragmentsTags.get(backStackCount - 2);
            setFragmentVisibilities(newTopFragmentTag);

            mFragmentsTags.remove(topFragmentTag);

            mExitCount = 0;
        }
        else if( backStackCount == 1){
            String topFragmentTag = mFragmentsTags.get(backStackCount - 1);
            if(topFragmentTag.equals(getString(R.string.tag_fragment_home))){
                mHomeFragment.scrollToTop();
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
            else{
                mExitCount++;
                Toast.makeText(this, "1 more click to exit", Toast.LENGTH_SHORT).show();
            }
        }

        if(mExitCount >= 2){
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.bottom_nav_home: {
                mFragmentsTags.clear();
                mFragmentsTags = new ArrayList<>();
                if(mHomeFragment == null){
                    mHomeFragment = new  MainHomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
                    transaction.commit();
                    mFragmentsTags.add(getString(R.string.tag_fragment_home));
                    mFragments.add(new FragmentTag(mHomeFragment, getString(R.string.tag_fragment_home)));
                }  else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_home));
                    mFragmentsTags.add(getString(R.string.tag_fragment_home));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_home));
                break;
            }

            case R.id.bottom_nav_category: {
                if(mCategoryFragment == null){
                mCategoryFragment = new CategoryFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mCategoryFragment, getString(R.string.tag_fragment_category));
                transaction.commit();
                mFragmentsTags.add(getString(R.string.tag_fragment_category));
                mFragments.add(new FragmentTag(mCategoryFragment, getString(R.string.tag_fragment_category)));
            }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_category));
                    mFragmentsTags.add(getString(R.string.tag_fragment_category));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_category));
                break;
            }

            case R.id.bottom_nav_report: {
                if (mMyReportsFragment == null){
                mMyReportsFragment = new  MyReportsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main_content_frame, mMyReportsFragment, getString(R.string.tag_fragment_my_report));
                transaction.commit();
                mFragmentsTags.add(getString(R.string.tag_fragment_my_report));
                mFragments.add(new FragmentTag(mMyReportsFragment, getString(R.string.tag_fragment_my_report)));
                }
                else {
                    mFragmentsTags.remove(getString(R.string.tag_fragment_my_report));
                    mFragmentsTags.add(getString(R.string.tag_fragment_my_report));
                }
                setFragmentVisibilities(getString(R.string.tag_fragment_my_report));
                break;
            }
        }
        return false;
    }
    private void init(){
        if(mHomeFragment == null){
        mHomeFragment = new  MainHomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_content_frame, mHomeFragment, getString(R.string.tag_fragment_home));
        transaction.commit();
       mFragmentsTags.add(getString(R.string.tag_fragment_home));
       mFragments.add(new FragmentTag(mHomeFragment, getString(R.string.tag_fragment_home)));
    }  else {
            mFragmentsTags.remove(getString(R.string.tag_fragment_home));
            mFragmentsTags.add(getString(R.string.tag_fragment_home));
        }
        setFragmentVisibilities(getString(R.string.tag_fragment_home));
    }
    private void setFragmentVisibilities(String tagname){
        for(int i = 0; i < mFragments.size(); i++){
            if(tagname.equals(mFragments.get(i).getTag())){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show((mFragments.get(i).getFragment()));
                transaction.commit();
            }
            else{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide((mFragments.get(i).getFragment()));
                transaction.commit();
            }
        }
    }
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);
        mBottomNavigationViewEx = findViewById(R.id.bottom_nav_view);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddReportDialog dialog = new AddReportDialog();
                dialog.show(getSupportFragmentManager(), "AddReportDialog");

            }
        });
        initBottomNavigationView();
        init();
    }
    private void initBottomNavigationView() {
        mBottomNavigationViewEx.enableAnimation(false);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);
    }

}