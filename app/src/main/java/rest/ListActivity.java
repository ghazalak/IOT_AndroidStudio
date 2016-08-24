package rest;

import Adapters.ButtonsExpandableListAdapter;
import rest.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.scurab.android.colorpicker.MainActivity;

public class ListActivity extends Activity {
    ButtonsExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buttons_background_layout);
        expListView = (ExpandableListView) findViewById(R.id.buttons_explv);
        listAdapter = new ButtonsExpandableListAdapter(this,expListView);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {return false;}
        });
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {}
        });
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {}
        });
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }
    public void buttons_BarnameRiziClickHandler(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
    public void abs(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}