package com.example.anhealthapp;

public class BMICalUtil
{
    public static final BMICalUtil instance =new BMICalUtil();
    public static BMICalUtil getInstance()
    {
        return instance;
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
}