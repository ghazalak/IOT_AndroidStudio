package Model;

import java.util.ArrayList;

public abstract class ObjectPort {
    protected long device_id;
    protected String name;
    protected ObjectDevice device;
    protected int index;
    protected long id;
    protected ArrayList<ObjectSchedule> schedules = new ArrayList<ObjectSchedule>();

    public ObjectDevice getDevice()
    {
        return device;
    }
    public void setDevice(ObjectDevice device) {
        this.device = device;
    }

    public ObjectPort(){

    }

    //virtual method
    public void setValue(String value) { }

    public String getName()
    {
        return name;
    }
    public int getIndex() {return index;}
    public long getDeviceId(){return device_id;}

    //    public void setValue(Integer percent) { this.per; }
    public void AddSchedule(ObjectSchedule sched) { this.schedules.add(sched); }

    public abstract String getSetUrl(ObjectDevice device, String status) ;

    public abstract String getGetUrl(ObjectDevice device) ;
}
