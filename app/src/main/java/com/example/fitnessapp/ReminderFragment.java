package com.example.fitnessapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


public class ReminderFragment extends Fragment {
    TextView foodCount, waterCount;
    CheckBox fruitA, fruitB, fruitC, vegA, vegB, vegC, vegD, proA, proB, proC, grainA, grainB, grainC, fatsA, fatsB, fatsC, waterA, waterB, waterC, waterD, waterE, waterF, waterG, waterH;
    int foodCounter, waterCounter;

    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false);

    }

    @Override
    public void onViewCreated(View viewer, Bundle savedInstanceState) {
        super.onViewCreated(viewer, savedInstanceState);
        foodCount = viewer.findViewById(R.id.servingFoodTextView);
        waterCount = viewer.findViewById(R.id.servingWaterTextView);
        fruitA = viewer.findViewById(R.id.fruitACheckBox);
        fruitB = viewer.findViewById(R.id.fruitBCheckBox);
        fruitC = viewer.findViewById(R.id.fruitCCheckBox);
        vegA = viewer.findViewById(R.id.vegACheckBox);
        vegB = viewer.findViewById(R.id.vegBCheckBox);
        vegC = viewer.findViewById(R.id.vegCCheckBox);
        vegD = viewer.findViewById(R.id.vegDCheckBox);
        proA = viewer.findViewById(R.id.proACheckBox);
        proB = viewer.findViewById(R.id.proBCheckBox);
        proC = viewer.findViewById(R.id.proCCheckBox);
        grainA = viewer.findViewById(R.id.grainACheckBox);
        grainB = viewer.findViewById(R.id.grainBCheckBox);
        grainC = viewer.findViewById(R.id.grainCCheckBox);
        fatsA = viewer.findViewById(R.id.fatsACheckBox);
        fatsB = viewer.findViewById(R.id.fatsBCheckBox);
        fatsC = viewer.findViewById(R.id.fatsCCheckBox);
        waterA = viewer.findViewById(R.id.waterACheckBox);
        waterB = viewer.findViewById(R.id.waterBCheckBox);
        waterC = viewer.findViewById(R.id.waterCCheckBox);
        waterD = viewer.findViewById(R.id.waterDCheckBox);
        waterE = viewer.findViewById(R.id.waterECheckBox);
        waterF = viewer.findViewById(R.id.waterFCheckBox);
        waterG = viewer.findViewById(R.id.waterGCheckBox);
        waterH = viewer.findViewById(R.id.waterHCheckBox);

        displayFruitServ();
        displayWaterServ();

        //displayFood();
        //displayWater();




    }

    public void displayFruitServ() {
        // Fruit
        if (fruitA.isChecked()) {
            foodCounter++;
        }
        else {
            foodCounter--;
        }

        if (fruitB.isChecked()) {
            foodCounter++;
        }
        else {
            foodCounter--;
        }

        if (fruitC.isChecked()) {
            foodCounter++;
        }
        else {
            foodCounter--;
        }
    }

    public void displayWaterServ() {
        // Water

        if (waterA.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterB.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterC.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterD.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterE.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterF.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterG.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }

        if (waterH.isChecked()) {
            waterCounter++;
        }
        else {
            waterCounter--;
        }
    }

    public void displayFood() {
        foodCount.setText(String.valueOf(foodCounter));
    }

    public void displayWater() {
        waterCount.setText(String.valueOf(waterCounter));
    }
}