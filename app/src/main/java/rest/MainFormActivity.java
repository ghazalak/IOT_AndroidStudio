package rest;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import rest.R;

public class MainFormActivity extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tabhosts);
        ActionBar actionBar=getActionBar();
        actionBar.hide();
        if (Models.Groups == null) {
            Models.Load(this.getApplicationContext());
        }
        TabHost mTabHost = getTabHost();
        mTabHost.addTab(mTabHost.newTabSpec("لیست").setIndicator("لیست").setContent(new Intent(this, ListActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("تنظیمات").setIndicator("تنظیمات").setContent(new Intent(this, SettingActivity.class)));
        mTabHost.setCurrentTab(0);
    }
}
