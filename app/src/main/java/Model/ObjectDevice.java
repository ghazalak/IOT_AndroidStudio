package Model;

import java.util.ArrayList;

public class ObjectDevice {
    long group_id;
    String name;
    int type;
    String address;
//    DeviceType type;
    long id;
    int portCount;
    ArrayList<ObjectPort> ports = new ArrayList<ObjectPort>();
    public ObjectDevice(long id, String name, int type, String address, int portCount, long group_id) {
        this.id = id;
        this.group_id = group_id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.portCount=ports.size();
        this.portCount=portCount;
    }
    public long getId(){return id;}
    public long getGroupId(){return group_id;}
    public Integer getPortsCount() {
        return ports.size();
    }
    public String getName() {
        return this.name;
    }
    public String getAddress() { return this.address; }
    public ObjectPort getKeyByIndex(int idx){
        for (int i = 0 ; i< ports.size();i++)
        {
            if (ports.get(i).getIndex() == idx)
                return ports.get(i);
        }
        return null;
    }
    public ObjectPort getKey(int i) {
        return ports.get(i);
    }
    public void AddKey(ObjectPort key) {
        key.device = this;
        ports.add(key);
    }

    public void addPort(ObjectPort port) {
        this.ports.add(port);
    }
    public int getTypeDevice(){return this.type;}
}
