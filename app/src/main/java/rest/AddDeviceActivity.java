package rest;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rest.R;

public class AddDeviceActivity extends Activity {
    String CreateTable="CREATE TABLE IF NOT EXISTS ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);
//        final SQLiteDatabase db = openOrCreateDatabase("all", MODE_PRIVATE, null);
//        db.execSQL(CreateTable+"device(id INT,name VARCHAR, type VARCHAR);");
//        ((Button)findViewById(R.id.AddDevice)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String deviceName=((EditText)findViewById(R.id.deviceName)).getText().toString();
//                String deviceType=((EditText)findViewById(R.id.deviceType)).getText().toString();
//                String deviceIP=((EditText)findViewById(R.id.deviceIP)).getText().toString();
//                db.execSQL("INSERT INTO student VALUES('" + deviceName + "','" + deviceType + "'," + deviceIP + ");");
//            }
//        });
    }
//    public void AddDeviceClickHandler(View view){
//        Intent intent=new Intent(this,rest.AddKeyActivity.class);
//        startActivity(intent);
//    }
}
