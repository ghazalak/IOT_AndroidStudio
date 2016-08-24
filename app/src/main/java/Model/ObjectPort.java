package Model;

import java.util.ArrayList;

public class ObjectPort {
    long device_id;
    String name;
    boolean status;
    ObjectDevice device;
    int index;
    long id;
    ArrayList<ObjectSchedule> schedules = new ArrayList<ObjectSchedule>();

    public ObjectDevice getDevice()
    {
        return device;
    }
    public void setDevice(ObjectDevice device) {
        this.device = device;
    }
    public ObjectPort(long id, String name, boolean status, int index, long device_id) {
        this.device_id = device_id;
        this.id = id;
        this.name = name;
        this.status= status;
        this.index=index;
    }
    public boolean getStatus()
    {
        return status;
    }
    public String getName()
    {
        return name;
    }
    public int getIndex() {return index;}
    public long getDeviceId(){return device_id;}
    public void setStatus(boolean status) { this.status = status; }
    public void AddSchedule(ObjectSchedule sched) { this.schedules.add(sched); }
}
