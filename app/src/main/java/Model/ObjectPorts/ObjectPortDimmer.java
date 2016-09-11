package Model.ObjectPorts;

import Model.ObjectDevice;
import Model.ObjectPort;

public class ObjectPortDimmer extends ObjectPort {
    public ObjectPortDimmer(long id, String name, int index, long device_id) {
        this.device_id = device_id;
        this.id = id;
        this.name = name;
        this.index=index;
    }
    int percent;
    @Override
    public void setValue(String value) {
        try {
            this.percent = Integer.valueOf(value);
        }catch (Exception e) {}
    }
    public int getValue()
    {
        return percent;
    }
    public String getSetUrl(ObjectDevice device, String value) {
        return "http://" + device.getAddress() + "/Dimmer?Dimmer=" + value;
    }
    public String getGetUrl(ObjectDevice device) {
        return "http://" + device.getAddress() + "/get_status" ;
    }
}
