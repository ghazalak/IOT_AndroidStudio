package Model;

import java.util.ArrayList;

public class ObjectGroup {
    String name;
    long id;
    ArrayList<ObjectDevice> devices = new ArrayList<ObjectDevice>();
    public ObjectGroup(long id, String name) {
        this.id = id;
       this.name = name;
    }
    public void addDevice(ObjectDevice device){devices.add(device);}
    public int getDeviceCount() {return devices.size();}
    public String getName() {return name;}
    public ObjectDevice getDevice(int id)
    {
        return this.devices.get(id);
    }
    public long getId(){return this.id;}
}
