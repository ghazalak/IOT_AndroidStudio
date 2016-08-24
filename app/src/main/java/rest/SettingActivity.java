package rest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import Adapters.SettingExpandableListAdapter;
import rest.R;

public class SettingActivity extends Activity {
    SettingExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_background_layout);
        expListView = (ExpandableListView) findViewById(R.id.setting_explv);
        listAdapter = new SettingExpandableListAdapter(this);
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;}
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
    public void btnAddGroupHandler(final View view){
        LayoutInflater li = LayoutInflater.from(this);
        final View promptsView = li.inflate(R.layout.setting_addgroup_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false).setPositiveButton("تایید",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    EditText editText=(EditText)promptsView.findViewById(R.id.groupName);
                    Models.InsertGroup(editText.getText().toString());
                    Models.Load(getApplicationContext());

                    expListView = (ExpandableListView) findViewById(R.id.setting_explv);
                    expListView.invalidate();
                    expListView.invalidateViews();
                }
            })
                .setNegativeButton("بازگشت",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                    });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}