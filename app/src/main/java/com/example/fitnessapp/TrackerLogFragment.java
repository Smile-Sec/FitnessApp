package com.example.fitnessapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackerLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackerLogFragment extends Fragment {

    Button addbutt;
    ListView todoerlist;
    EditText editerlist;
    ListAdapter MyAdapter;
    SQLiteDatabase db;
    String toDoString;
    ArrayList<ListObject> elementList = new ArrayList<>();

    public TrackerLogFragment() {
        // Required empty public constructor
    }

    public static TrackerLogFragment newInstance() {
        TrackerLogFragment fragment = new TrackerLogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracker_log, container, false);
    }

    @Override
    public void onViewCreated(View viewer, Bundle savedInstanceState) {
        super.onViewCreated(viewer, savedInstanceState);
        addbutt = viewer.findViewById(R.id.addButton);
        todoerlist = viewer.findViewById(R.id.todolist);
        editerlist = viewer.findViewById(R.id.editlist);

        loadDataFromDatabase();

        addbutt.setOnClickListener(view -> {
            addDataToDatabase();


        });

        todoerlist.setAdapter(MyAdapter = new ListAdapter());

        todoerlist.setOnItemLongClickListener((p, b, pos, id) -> {
            showToDo(pos);
            return true;
        });



        SwipeRefreshLayout refresher = viewer.findViewById(R.id.refresher);
        refresher.setOnRefreshListener( () -> refresher.setRefreshing(false)  );
    }

    private void addDataToDatabase() {
        SQLdb openDB = new SQLdb(getContext());
        db = openDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        toDoString = editerlist.getText().toString();
        cv.put(SQLdb.COL_NAME, toDoString);
        long newId = db.insert(SQLdb.TABLE_NAME, null, cv);
        ListObject obj = new ListObject(toDoString, newId);
        elementList.add(obj);
        editerlist.setText(" ");
        MyAdapter.notifyDataSetChanged();
    }

    private void loadDataFromDatabase() {
        SQLdb openDB = new SQLdb(getContext());
        db = openDB.getReadableDatabase();

        String [] columns = {
                SQLdb.COL_ID, SQLdb.COL_NAME
        };

        Cursor results = db.query(false, SQLdb.TABLE_NAME, columns, null, null, null, null, null, null);

        int nameColIndex = results.getColumnIndex(SQLdb.COL_NAME);
        int idColIndex = results.getColumnIndex(SQLdb.COL_ID);

        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            long id = results.getLong(idColIndex);
            elementList.add(new ListObject(name, id));
        }

    }

    protected void showToDo(int position) {

        ListObject selectObj = elementList.get(position);

        View activity_view = getLayoutInflater().inflate(R.layout.activity_list, null);

        TextView rowTxt = activity_view.findViewById(R.id.textView2);

        rowTxt.setText(selectObj.getName());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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

            return newView;
        }

    }

    public class ListObject {
        String toDoString;
        long id;

        public ListObject(String toDoString, long id) {
            this.toDoString = toDoString;
            this.id = id;
        }

        public void update(String toDoString) {
            this.toDoString = toDoString;
        }

        public ListObject(String toDoString) {
            this (toDoString, 0);
        }

        public String getName() {
            return toDoString;
        }

        public long getId() {
            return id;
        }

    }
}