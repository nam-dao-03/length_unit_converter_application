package com.example.lengthunitconverterapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lengthunitconverterapplication.adapters.UnitAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_main;
    private TextView tv_convert_to, tv_to_convert;
    private TextView tv_unit_to_convert, tv_unit_convert_to;
    private Spinner spn_convert_to, spn_to_convert;
    private EditText et_unit;
    private Button btn_reset,btn_convert;

    //
    private UnitAdapter unitAdapter;
    private List<UnitAdapter.Unit> unitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
        setListenerForWidgets();
    }

    private void initUI() {
        ll_main = findViewById(R.id.main);
        tv_convert_to = findViewById(R.id.tv_convert_to);
        tv_to_convert = findViewById(R.id.tv_to_convert);
        tv_unit_to_convert = findViewById(R.id.tv_unit_to_convert);
        tv_unit_convert_to = findViewById(R.id.tv_unit_convert_to);
        spn_to_convert = findViewById(R.id.spn_to_convert);
        spn_convert_to = findViewById(R.id.spn_convert_to);
        et_unit = findViewById(R.id.et_unit);
        btn_reset = findViewById(R.id.btn_reset);
        btn_convert = findViewById(R.id.btn_convert);
        showDropdownUnit();
    }
    private void setListenerForWidgets(){
        ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_unit.clearFocus();
            }
        });
        spn_convert_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UnitAdapter.Unit unitItem = ((UnitAdapter.Unit) parent.getItemAtPosition(position));
                tv_unit_convert_to.setText(unitItem.getName());
                resetValue(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_to_convert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UnitAdapter.Unit unitItem = ((UnitAdapter.Unit) parent.getItemAtPosition(position));
                tv_unit_to_convert.setText(unitItem.getName());
                resetValue(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValue(true);
            }
        });
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double initialValue = Double.parseDouble(et_unit.getText().toString());
                    if(initialValue < 0 || initialValue > 1000) {
                        createToast("Number must be > 0 and < 1000", R.drawable.baseline_warning_24);
                        return;
                    }
                    convertUnit(initialValue, tv_unit_convert_to.getText().toString(), tv_unit_to_convert.getText().toString());
                } catch (Exception e) {
                    createToast("Invalid Value", R.drawable.baseline_warning_24);
                }
            }
        });
    }
    private void showDropdownUnit(){
        unitList.add(new UnitAdapter.Unit("Metre"));
        unitList.add(new UnitAdapter.Unit("Millimetre"));
        unitList.add(new UnitAdapter.Unit("Mile"));
        unitList.add(new UnitAdapter.Unit("Foot"));
        unitAdapter = new UnitAdapter(MainActivity.this, R.layout.item_dropdown_selected, unitList);
        spn_convert_to.setAdapter(unitAdapter);
        spn_to_convert.setAdapter(unitAdapter);
    }
    private void convertUnit(Double initialValue, String unitConvertTo, String unitToConvert) {
        double convertedValue = 0.0;

        if (unitConvertTo.equals("Metre")) {
            switch (unitToConvert) {
                case "Millimetre":
                    convertedValue = initialValue * 1000;
                    break;
                case "Mile":
                    convertedValue = initialValue / 1609.34;
                    break;
                case "Foot":
                    convertedValue = initialValue * 3.28084;
                    break;
                default:
                    convertedValue = initialValue;
                    break;
            }
        } else if (unitConvertTo.equals("Millimetre")) {
            switch (unitToConvert) {
                case "Metre":
                    convertedValue = initialValue / 1000;
                    break;
                case "Mile":
                    convertedValue = initialValue / 1609340;
                    break;
                case "Foot":
                    convertedValue = initialValue / 304.8;
                    break;
                default:
                    convertedValue = initialValue;
                    break;
            }
        } else if (unitConvertTo.equals("Mile")) {
            switch (unitToConvert) {
                case "Metre":
                    convertedValue = initialValue * 1609.34;
                    break;
                case "Millimetre":
                    convertedValue = initialValue * 1609340;
                    break;
                case "Foot":
                    convertedValue = initialValue * 5280;
                    break;
                default:
                    convertedValue = initialValue;
                    break;
            }
        } else if (unitConvertTo.equals("Foot")) {
            switch (unitToConvert) {
                case "Metre":
                    convertedValue = initialValue / 3.28084;
                    break;
                case "Millimetre":
                    convertedValue = initialValue * 304.8;
                    break;
                case "Mile":
                    convertedValue = initialValue / 5280;
                    break;
                default:
                    convertedValue = initialValue;
                    break;
            }
        }
        displayValue(convertedValue, initialValue);
    }
    private void displayValue(double convertedValue,double initialValue) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        tv_convert_to.setText(decimalFormat.format(initialValue));
        tv_to_convert.setText(decimalFormat.format(convertedValue));
    }

    private void createToast(String input_text_to_toast, int imageResId){
        Toast toast = new Toast(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_custom_toast, findViewById(R.id.layout_custom_toast));
        TextView text_toast = view.findViewById(R.id.text_toast);
        ImageView img_icon_toast = view.findViewById(R.id.img_icon_toast);
        text_toast.setText(input_text_to_toast);
        img_icon_toast.setImageResource(imageResId);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    private void resetValue(boolean isDeleteInput){
        tv_to_convert.setText("0");
        tv_convert_to.setText("0");
        if(!isDeleteInput) return;
        et_unit.clearFocus();
        et_unit.setText("");
    }
}