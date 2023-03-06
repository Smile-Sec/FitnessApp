package com.example.fitnessapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class logTracker extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar tool;
    Button addbutt;
    ListView todoerlist;
    EditText editerlist;
    Switch switches;
    ListAdapter MyAdapter;
    SQLiteDatabase db;
    String toDoString;
    Boolean urgency;
    Integer boole;
    ArrayList<ListObject> elementList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_tracker);
        addbutt = findViewById(R.id.addButton);
        todoerlist = findViewById(R.id.todolist);
        editerlist = findViewById(R.id.editlist);
        switches = findViewById(R.id.switch2);
        tool = findViewById(R.id.my_ttoolbar);
        setSupportActionBar(tool);
        DrawerLayout draw = findViewById(R.id.my_ddrawer);
        ActionBarDrawerToggle abdToggle = new ActionBarDrawerToggle(this, draw, tool, R.string.open, R.string.close);
        draw.addDrawerListener(abdToggle);
        abdToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navi_View);
        navigationView.setNavigationItemSelectedListener(this);
        loadDataFromDatabase();


        addbutt.setOnClickListener(view -> {
            ContentValues cv = new ContentValues();
            toDoString = editerlist.getText().toString();
            urgency = switches.isChecked();
            convertBoolToInt();
            cv.put(SQLdb.COL_NAME, toDoString);
            cv.put(SQLdb.COL_BOOL, boole);
            long newId = db.insert(SQLdb.TABLE_NAME, null, cv);
            ListObject obj = new ListObject(toDoString, boole, newId);
            elementList.add(obj);
            editerlist.setText(" ");
            switches.setChecked(false);
            MyAdapter.notifyDataSetChanged();
        });

        todoerlist.setAdapter(MyAdapter = new ListAdapter());

        todoerlist.setOnItemLongClickListener((p, b, pos, id) -> {

            showToDo(pos);
            return true;
        });


        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String message = null;
        switch(item.getItemId()) {
            case R.id.fitnesshome:
                Intent nextPage = new Intent(logTracker.this, MainActivity.class);
                startActivity(nextPage);
                break;
            case R.id.fitnessworkout:
                Intent nextPages = new Intent(logTracker.this, Workout.class);
                startActivity(nextPages);
                break;
            case R.id.fitnesslog:
                message = "You are already on the Fitness Logger silly!";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    public int convertBoolToInt() {
        if(urgency) {
            boole = 1;
        }
        else {
            boole = 0;
        }
        return boole;
    }

    private void loadDataFromDatabase() {
        SQLdb openDB = new SQLdb(this);
        db = openDB.getWritableDatabase();


        String [] columns = {
                SQLdb.COL_ID, SQLdb.COL_NAME , SQLdb.COL_BOOL
        };

        Cursor results = db.query(false, SQLdb.TABLE_NAME, columns, null, null, null, null, null, null);

        int boolColumnIndex = results.getColumnIndex(SQLdb.COL_BOOL);
        int nameColIndex = results.getColumnIndex(SQLdb.COL_NAME);
        int idColIndex = results.getColumnIndex(SQLdb.COL_ID);

        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            int bool = results.getInt(boolColumnIndex);
            long id = results.getLong(idColIndex);


            elementList.add(new ListObject(name, bool, id));
        }

    }

    protected void showToDo(int position) {

        ListObject selectObj = elementList.get(position);

        View activity_view = getLayoutInflater().inflate(R.layout.activity_list, null);

        TextView rowTxt = activity_view.findViewById(R.id.textView2);

        rowTxt.setText(selectObj.getName());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(R.string.doyoutitle))
                .setMessage(getResources().getString(R.string.therowmsg) + " " + position)
                .setView(activity_view)
                .setPositiveButton(getResources().getString(R.string.yesbutt), (click, arg) -> {
                    deleteToDo(selectObj);
                    elementList.remove(position);
                    MyAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(getResources().getString(R.string.nobutt), (click,arg) -> {})
                .create().show();

    }

    protected void deleteToDo(ListObject c) {
        db.delete(SQLdb.TABLE_NAME, SQLdb.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }


    class ListAdapter extends BaseAdapter {

        public int getCount() {
            return elementList.size();
        }

        public ListObject getItem(int position) {
            return elementList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View newView = convertView;
            LayoutInflater inflater = getLayoutInflater();

            ListObject thisRow = getItem(position);
            if(newView == null) {
                newView = inflater.inflate(R.layout.activity_list, parent, false);
            }
            TextView newTxt = newView.findViewById(R.id.textView2);

            newTxt.setText(thisRow.getName());


            if(getItem(position).boole == 1) {
                newView.setBackgroundColor(Color.RED);
                newTxt.setTextColor(Color.WHITE);
            }

            return newView;
        }

    }

    public class ListObject {
        String toDoString;
        Integer boole;
        long id;

        public ListObject(String toDoString, Integer boole, long id) {
            this.toDoString = toDoString;
            this.boole = boole;
            this.id = id;
        }

        public void update(String toDoString, Integer boole) {
            this.toDoString = toDoString;
            this.boole = boole;
        }

        public ListObject(String toDoString, Integer boole) {
            this (toDoString, boole, 0);
        }

        public int getBool() {
            return boole;
        }

        public String getName() {
            return toDoString;
        }

        public long getId() {
            return id;
        }

    }

}