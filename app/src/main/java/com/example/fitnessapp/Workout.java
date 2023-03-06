package com.example.fitnessapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Workout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        tool = findViewById(R.id.my_tttoolbar);
        setSupportActionBar(tool);
        DrawerLayout draw = findViewById(R.id.my_dddrawer);
        ActionBarDrawerToggle abdToggle = new ActionBarDrawerToggle(this, draw, tool, R.string.open, R.string.close);
        draw.addDrawerListener(abdToggle);
        abdToggle.syncState();

        NavigationView navigationView = findViewById(R.id.naviiView);
        navigationView.setNavigationItemSelectedListener(this);

        };




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId()) {
            case R.id.fitnesshome:
                Intent nextPage = new Intent(Workout.this, MainActivity.class);
                startActivity(nextPage);
                break;
            case R.id.fitnessworkout:
                message = "You are already on the Fitness Logger silly!";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.fitnesslog:
                Intent nextPages = new Intent(Workout.this, logTracker.class);
                startActivity(nextPages);
                break;
            case R.id.fitnessexit:
                finishAffinity();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.my_ddrawer);
        drawerLayout.closeDrawer(GravityCompat.START);

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