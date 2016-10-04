package Model;

import java.util.ArrayList;

public class ObjectDevice {
    public final static int DEVICE_TYPE_KEY = 1;
    public final static int DEVICE_TYPE_SOCKET = 2;
    public final static int DEVICE_TYPE_LED_RGB = 3;
    public final static int DEVICE_TYPE_DIMMER = 4;

    long group_id;
    String name;
    int type;
    String address;
//    DeviceType type;
    long id;
    int portCount;
    ArrayList<ObjectPort> ports = new ArrayList<ObjectPort>();
    public ObjectDevice(long id, String name, int type, String address, long group_id) {
        this.id = id;
        this.group_id = group_id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.portCount=ports.size();

    }
    public long getId(){return id;}
    public long getGroupId(){return group_id;}
    public int getPortsCount() {
        return ports.size();
    }
    public String getName() {
        return this.name;
    }
    public String getAddress() { return this.address; }
    public ObjectPort getPortByIndex(int idx){
        for (int i = 0 ; i< ports.size();i++)
        {
            if (ports.get(i).getIndex() == idx)
                return ports.get(i);
        }
        return null;
    }
//    public ObjectPort getPort(int i) {
//        return ports.get(i);
//    }
    public void AddPort(ObjectPort port) {
        port.device = this;
        ports.add(port);
    }

    public int getType(){return this.type;}

    public ArrayList<ObjectPort> getPorts() {
        return ports;
    }
}
