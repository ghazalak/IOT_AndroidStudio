package Model.ObjectPorts;

import java.util.ArrayList;
import Model.ObjectDevice;
import Model.ObjectPort;

public class ObjectPortLedRgb extends ObjectPort {
    public ObjectPortLedRgb(long id, String name, int index, long device_id) {
        this.device_id = device_id;
        this.id = id;
        this.name = name;
        this.index=index;
    }
    int Red;
    int Green;
    int Blue;

    public void setValue(String value) {
        try {
            ArrayList<Integer> result = convertString2(value);
            this.Red = result.get(0);
            this.Green = result.get(1);
            this.Blue = result.get(2);
        }
        catch(Exception e)
        {

        }
    }
    private ArrayList<Integer> convertString(String value) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(Integer.valueOf(value.substring(3, 5), 16));
        result.add(Integer.valueOf(value.substring(5, 7), 16));
        result.add(Integer.valueOf(value.substring(7, 9), 16));
        return result;
    }
    private ArrayList<Integer> convertString2(String value) {

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(Integer.valueOf(value.substring(0, 3)));
        result.add(Integer.valueOf(value.substring(3, 6)));
        result.add(Integer.valueOf(value.substring(6, 9)));
        return result;
    }
    public int getRed() {
        return this.Red;
    }

    public int getBlue() {
        return this.Blue;
    }

    public int getGreen() {
        return this.Green;
    }

    public String getSetUrl(ObjectDevice device, String value) {
        ArrayList<Integer> result = convertString( value);

        return "http://192.168.1.1/RGB?Red=" + result.get(0) + "&Green=" + result.get(1) + "&Blue=" + result.get(2);
    }
    public String getGetUrl(ObjectDevice device) {
        return "http://" + device.getAddress() + "/get_status";
    }
}
