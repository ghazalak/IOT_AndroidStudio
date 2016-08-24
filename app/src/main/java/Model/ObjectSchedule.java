package Model;

import java.util.ArrayList;

public class ObjectSchedule {
    int hour;
    long id;
    int minute;
    long port_id;
    boolean status;
    boolean repeat;
    boolean days0;
    boolean days1;
    boolean days2;
    boolean days3;
    boolean days4;
    boolean days5;
    boolean days6;


//    ArrayList<boolean> days=new ArrayList<boolean>();


    public ObjectSchedule(
            long id,
            int hour,
            int minute,
            boolean status,
            boolean repeat,
            boolean days0,
            boolean days1,
            boolean days2,
            boolean days3,
            boolean days4,
            boolean days5,
            boolean days6,
            long port_id)
    {
        this.hour = hour;
        this.id= id;
        this.minute = minute;
        this.port_id = port_id;
        this.status = status;
        this.repeat = repeat;
        this.days0 = days0;
        this.days1 = days1;
        this.days2 = days2;
        this.days3 = days3;
        this.days4 = days4;
        this.days5 = days5;
        this.days6 = days6;

    }
    public long getPortId(){
        return this.port_id;
    }
}
