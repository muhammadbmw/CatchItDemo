package com.breezemaxweb.catchit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Calendar extends AppCompatActivity {
    CalendarView simpleCalendarView;
    ListView simpleList;
    ArrayList<Schedules> slist = new ArrayList<Schedules>();

    ScheduleViewAdapter adapter;
    int day = 0, mth = 0, yr = 0;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        this.setTitle("CALENDAR");
        simpleCalendarView = (CalendarView)findViewById(R.id.calendarView);

        simpleList =(ListView) findViewById(R.id.listschedule);
        slist.add(new Schedules(3,4,2017,"Aprix Media","Discovery Session","11:00AM - In Person" ));
        slist.add(new Schedules(3,4,2017,"Atlas Packaging","Discovery Session","2:00PM - In Person" ));
        slist.add(new Schedules(4,4,2017,"Bermain & co","Discovery Session","10:00AM - In Person" ));
        slist.add(new Schedules(4,4,2017,"Media Solution","Discovery Session","3:00PM - In Person" ));
        adapter = new ScheduleViewAdapter(this,slist);
        simpleList.setAdapter(adapter);

        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // display the selected date by using a toast
                month++;
                day = dayOfMonth;
                mth = month;
                yr = year;
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                adapter.filter(dayOfMonth,month,year);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.addEntry){
          //if no prospect selected asked for prospect select
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.attach_prospect);
            Button select = (Button) dialog.findViewById(R.id.select);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            //click the save button
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(),SelectProspect.class);
                    if(day !=0 && mth!=0 && yr!=0){
                        intent.putExtra("day", day);
                        intent.putExtra("month", mth);
                        intent.putExtra("year", yr);
                    }
                    startActivity(intent);
                }

            });
            // click the cancel
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            //end prospect assgined pop up
          //  Intent intent = new Intent(this, AddEvent.class);
          /*  Intent intent = new Intent(this,SelectProspect.class);
            if(day !=0 && mth!=0 && yr!=0){
                intent.putExtra("day", day);
                intent.putExtra("month", mth);
                intent.putExtra("year", yr);
            }
            startActivity(intent);*/
            return true;
        }
        else if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        else
         return super.onOptionsItemSelected(item);
    }
}
