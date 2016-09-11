package main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConfigureTestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration_layout);

    }
    public void Configure(View view){
        Intent intent = new Intent(this,Connect.class);
        startActivity(intent);
    }
}