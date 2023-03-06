package com.example.fitnessapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar tool;
    DrawerLayout draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tool = findViewById(R.id.my_toolbar);
        setSupportActionBar(tool);
        draw = findViewById(R.id.my_drawer);
        ActionBarDrawerToggle abdToggle = new ActionBarDrawerToggle(this, draw, tool, R.string.open, R.string.close);
        draw.addDrawerListener(abdToggle);
        abdToggle.syncState();

        NavigationView navigationView = findViewById(R.id.naviView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId()) {
            case R.id.fitnesshome:
                message = "You are already home silly!";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.fitnessworkout:
                Intent nextPages = new Intent(MainActivity.this, Workout.class);
                startActivity(nextPages);
                break;
            case R.id.fitnesslog:
                Intent nextPage = new Intent(MainActivity.this, logTracker.class);
                startActivity(nextPage);
                break;
            case R.id.fitnessexit:
                finishAffinity();
                break;
        }

        draw.closeDrawer(GravityCompat.START);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId())
        {
            case R.id.item1:
                message = "This button eventually opens settings";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fitness_menu, menu);

        return true;
    }
}