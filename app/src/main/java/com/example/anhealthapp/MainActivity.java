package com.example.anhealthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView heading1,bmi_score,message;
    private ToggleButton metrics;
    private EditText cm,ft,in,kg,lbs,age;
    private Button calculate;
    private boolean inMetrics;
    //private CardView cardView;

    private LinearLayout main;

    private Spinner gender;

    String[] gen = {"Male", "Female", "Others"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main=findViewById(R.id.ABC);
        heading1=findViewById(R.id.heading1);
        bmi_score=findViewById(R.id.bmi_score);
        message=findViewById(R.id.message);
        metrics=findViewById(R.id.metrics);
        cm=findViewById(R.id.cm);
        ft=findViewById(R.id.ft);
        in=findViewById(R.id.in);
        kg=findViewById(R.id.kg);
        lbs=findViewById(R.id.lbs);
        //gender=findViewById(R.id.gender);
        gender=findViewById(R.id.gender);
        age=findViewById(R.id.age);
        calculate=findViewById(R.id.calculate);
        //cardView=findViewById(R.id.cardView);



        bmi_score.setVisibility(View.VISIBLE);
        message.setVisibility(View.GONE);

        inMetrics=true;
        updateInputVisibility();



        metrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inMetrics=!inMetrics;
                updateInputVisibility();
            }
        });


        //SPINNER
        gender.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad= new ArrayAdapter(this,R.layout.gender,gen);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        gender.setAdapter(ad);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setActivityBackgroundColor(Color.parseColor("#87B1E3"));
                if (inMetrics)
                {
                    if (cm.length()== 0 || kg.length()==0)
                        Toast.makeText(MainActivity.this, "Enter Weight and Height to calculate BMI", Toast.LENGTH_SHORT).show();
                    else {
                        double heightCm= Double.parseDouble(cm.getText().toString());
                        double weightKg=Double.parseDouble(kg.getText().toString());
                        //double b=BMICalUtil.getInstance().calculateBMIMetric(heightCm,weightKg);
                        double b=calculateBMIMetric(heightCm,weightKg);
                        displayBMI(b);
                    }
                }
                else
                {
                    if(ft.length()==0 || in.length()==0 || lbs.length()==0)
                        Toast.makeText(MainActivity.this, "Enter Weight and Height to calculate BMI", Toast.LENGTH_SHORT).show();
                    else
                    {
                        double heightFt=Double.parseDouble(ft.getText().toString());
                        double heightIn=Double.parseDouble(in.getText().toString());
                        double weightLbs=Double.parseDouble(lbs.getText().toString());
                        //double b=BMICalUtil.getInstance().calculateBMIImperial(NoheightFt,heightIn,weightLbs);
                        double b=calculateBMIImperial(heightFt,heightIn,weightLbs);
                        displayBMI(b);
                    }
                }
            }
        });
    }
    private void updateInputVisibility()
    {
        if(inMetrics)
        {
            cm.setVisibility(View.VISIBLE);
            kg.setVisibility(View.VISIBLE);
            ft.setVisibility(View.GONE);
            in.setVisibility(View.GONE);
            lbs.setVisibility(View.GONE);
        }
        else
        {
            cm.setVisibility(View.GONE);
            kg.setVisibility(View.GONE);
            ft.setVisibility(View.VISIBLE);
            in.setVisibility(View.VISIBLE);
            lbs.setVisibility(View.VISIBLE);
        }
    }

    private void displayBMI(double b) {
        bmi_score.setVisibility(View.VISIBLE);
        message.setVisibility(View.VISIBLE);

        //heading1.setText(String.format());
        heading1.setText("YOUR RESULT");
        bmi_score.setText(String.format("%.2f",b));
        //String bmi_message=BMICalUtil.getInstance().classifyBMI(b);
        String bmi_message=classifyBMI(b);
        message.setText(bmi_message);
        bmi_score.setTextSize(70);

        Toast.makeText(this, bmi_message, Toast.LENGTH_SHORT).show();

        switch (bmi_message)
        {
            case "UNDERWEIGHT ! TIME FOR SOME MORE HEALTHY SNACKS !!" :

                main.setBackgroundResource(R.color.under);
                heading1.setTextColor(Color.parseColor("#F9F9F9"));
                bmi_score.setTextColor(Color.parseColor("#F9F9F9"));
                message.setTextColor(Color.parseColor("#F9F9F9"));
                break;
            case "OVERWEIGHT ! ":
                //cardView.setCardBackgroundColor(Color.YELLOW);
                main.setBackgroundResource(R.color.over);
                heading1.setTextColor(Color.parseColor("#F9F9F9"));
                bmi_score.setTextColor(Color.parseColor("#F9F9F9"));
                message.setTextColor(Color.parseColor("#F9F9F9"));
                break;
            case "YOU ARE IN GREAT SHAPE":
                //cardView.setCardBackgroundColor(Color.GREEN);

                main.setBackgroundResource(R.color.normal);
                heading1.setTextColor(Color.parseColor("#F9F9F9"));
                bmi_score.setTextColor(Color.parseColor("#F9F9F9"));
                message.setTextColor(Color.parseColor("#F9F9F9"));

                break;
            case "Obese !! START RUNNING FROM TODAY !" :
                //cardView.setCardBackgroundColor(Color.RED);

                main.setBackgroundResource(R.color.obese);
                heading1.setTextColor(Color.BLACK);
                bmi_score.setTextColor(Color.BLACK);
                message.setTextColor(Color.BLACK);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // Toast.makeText(getApplicationContext(),gen[position],Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    public void setActivityBackgroundColor(int color)
    {
        View view=this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }

    public String classifyBMI(double bmi)
    {
        if(bmi<18.5)
            return "UNDERWEIGHT ! TIME FOR SOME MORE HEALTHY SNACKS !!";
        else if (bmi >= 18.5 && bmi < 25)
            return  "YOU ARE IN GREAT SHAPE";
        else if (bmi >= 25 && bmi < 30)
            return "OVERWEIGHT ! ";
        else
            return "Obese !! START RUNNING FROM TODAY !";
    }

    public double calculateBMIMetric(double heightcm,double weightkg)
    {
        return (weightkg/((heightcm/100)*(heightcm/100)));
    }
    public double calculateBMIImperial(double heightFeet,double heightInches,double weightlbs)
    {
        double totalheightInches=(12*heightFeet)+heightInches;
        return ((703*weightlbs)/(totalheightInches*totalheightInches));
    }
}