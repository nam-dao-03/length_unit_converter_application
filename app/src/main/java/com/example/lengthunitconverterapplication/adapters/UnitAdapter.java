package com.example.lengthunitconverterapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lengthunitconverterapplication.R;

import java.util.List;

public class UnitAdapter extends ArrayAdapter<UnitAdapter.Unit> {


    public UnitAdapter(@NonNull Context context, int resource, @NonNull List<Unit> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_to_select, parent, false);
        TextView tv_unit = convertView.findViewById(R.id.tv_item_to_select);

        Unit unit = this.getItem(position);
        if(unit == null) return convertView;
        tv_unit.setText(unit.getName());
        return convertView;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dropdown_selected, parent, false);
        TextView tv_unit = convertView.findViewById(R.id.tv_item_selected);

        Unit unit = this.getItem(position);
        if(unit == null) return convertView;
        tv_unit.setText(unit.getName());
        return convertView;
    }

    public static class Unit {
        private String name;
        public Unit(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
