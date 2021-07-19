package com.example.reportingapp.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.reportingapp.R;
import com.example.reportingapp.models.Report;

import java.util.List;

public class  CategoryAdapter extends ArrayAdapter<String> {

        private int mLayoutResource;
        private Context mContext;
        public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            mContext = context;
            mLayoutResource = resource;
        }
        public static class ViewHolder{
            TextView category;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mLayoutResource, parent, false);
                holder = new ViewHolder();

                holder.category = convertView.findViewById(R.id.text_report);
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
                holder.category.setText("");
            }

            try{
                //set the message
                holder.category.setText(getItem(position));

            }catch (NullPointerException e){
            }

            return convertView;
        }

    }


