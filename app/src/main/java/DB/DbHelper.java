package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "iot", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE groups( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL);";

        db.execSQL(createTable);

        createTable = "CREATE TABLE devices( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL," +
                "type INTEGER NOT NULL," +
                "address TEXT NOT NULL," +
                "portCount INTEGER NOT NULL,"+
                "group_id INTEGER NOT NULL);";

        db.execSQL(createTable);

        createTable = "CREATE TABLE ports( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL," +
//                "status INTEGER NOT NULL," +
                "idx INTEGER NOT NULL," +
                "device_id INTEGER NOT NULL);";

        db.execSQL(createTable);

        createTable = "CREATE TABLE schedules( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hour INTEGER NOT NULL," +
                "minute INTEGER NOT NULL," +
                "status INTEGER NOT NULL," +
                "repeat INTEGER NOT NULL," +
                "days0 INTEGER NOT NULL," +
                "days1 INTEGER NOT NULL," +
                "days2 INTEGER NOT NULL," +
                "days3 INTEGER NOT NULL," +
                "days4 INTEGER NOT NULL," +
                "days5 INTEGER NOT NULL," +
                "days6 INTEGER NOT NULL," +
                "port_id INTEGER NOT NULL);";

        db.execSQL(createTable);


        ContentValues values;
        try {
            values = new ContentValues();
            values.put("name", "اتاقم");
            long group_id = db.insertOrThrow("groups", null, values);

            values = new ContentValues();
            values.put("name", "پریز کنار میز");
            values.put("type", 3);
            values.put("address", "192.168.1.1");
            values.put("portCount", 1);
            values.put("group_id", group_id);
            long device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "پریز کنار میز");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            long port_id = db.insertOrThrow("ports", null, values);


            values = new ContentValues();
            values.put("name", "چراغ دم در");
            values.put("type", 1);
            values.put("address", "192.168.1.1");
            values.put("portCount", 2);
            values.put("group_id", group_id);
            device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "چراغ وسط");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "چراغ حمام");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);


            values = new ContentValues();
            values.put("name", "پریز کنار کمد");
            values.put("type", 4);
            values.put("address", "192.168.1.1");
            values.put("portCount", 1);
            values.put("group_id", group_id);
            device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "پریز کنار کمد");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);


            values = new ContentValues();
            values.put("name", "سه راهی کنار تخت ");
            values.put("type", 2);
            values.put("address", "192.168.1.1");
            values.put("portCount", 3);
            values.put("group_id", group_id);
             device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "چراغ خواب");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
             port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "موبایل");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "پنکه");
            values.put("idx", 3);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "سه راهی روی میز");
            values.put("type", 2);
            values.put("address", "192.168.1.1");
            values.put("portCount", 4);
            values.put("group_id", group_id);
             device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "لپ تاپ");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
             port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "چراغ بالای میز");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "مودم");
            values.put("idx", 3);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "تلفن");
            values.put("idx", 4);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);


            values = new ContentValues();
            values.put("name", "پذیرایی");
             group_id = db.insertOrThrow("groups", null, values);

            values = new ContentValues();
            values.put("name", "پریز کنار تلویزیون");
            values.put("type", 2);
            values.put("address", "192.168.1.1");
            values.put("portCount", 4);
            values.put("group_id", group_id);
            device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "موبایل");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "تلویزیون");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "VCD");
            values.put("idx", 3);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "کلید4");
            values.put("idx", 4);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "کولر");
            values.put("type", 1);
            values.put("address", "192.168.1.1");
            values.put("portCount", 3);
            values.put("group_id", group_id);
            device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "آب");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "پمپ");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "سرعت");
            values.put("idx", 3);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "کلید لوستر");
            values.put("type", 1);
            values.put("address", "192.168.1.1");
            values.put("portCount", 2);
            values.put("group_id", group_id);
            device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "دور لوستر");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "وسط لوستر");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "کلید آشپزخانه");
            values.put("type", 1);
            values.put("address", "192.168.1.1");
            values.put("portCount", 2);
            values.put("group_id", group_id);
            device_id = db.insertOrThrow("devices", null, values);

            values = new ContentValues();
            values.put("name", "سقف");
            values.put("idx", 1);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

            values = new ContentValues();
            values.put("name", "هالوژن");
            values.put("idx", 2);
//            values.put("status", 0);
            values.put("device_id", device_id);
            port_id = db.insertOrThrow("ports", null, values);

        }
        catch(Exception e)
        {
            String a = e.toString();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS schedules" );
        db.execSQL("DROP TABLE IF EXISTS ports" );
        db.execSQL("DROP TABLE IF EXISTS devices" );
        db.execSQL("DROP TABLE IF EXISTS groups" );
        onCreate(db);
    }
}