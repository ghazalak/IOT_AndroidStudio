package Model.ObjectPorts;

import Model.ObjectDevice;
import Model.ObjectPort;

public class ObjectPortKey extends ObjectPort {
    public void setValue(String value) {
        if (value.length()>1)
            return;
        this.status = Integer.valueOf(value)==1?true:false;
    }
    public ObjectPortKey(long id, String name, int index, long device_id) {
        this.device_id = device_id;
        this.id = id;
        this.name = name;
        this.index=index;
    }
    boolean status;
    public boolean getStatus()
    {
        return status;
    }
    public String getSetUrl(ObjectDevice device, String value) {
        return "http://" + device.getAddress() + "/key" + index + (Boolean.valueOf(value)? "_off" : "_on");
    }
    public String getGetUrl(ObjectDevice device) {
        return "http://" + device.getAddress() + "/get_status" + index;
    }
}
