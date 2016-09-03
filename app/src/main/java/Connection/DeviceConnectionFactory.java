package Connection;

import main.IDeviceConnectionListener;

public class DeviceConnectionFactory {
    static boolean Http = true;
    static IDeviceConnection dev;
    public static IDeviceConnection GetDeviceConnection(IDeviceConnectionListener l) {
        if (dev == null){
            if (Http)
                dev = new HttpDeviceConnection();
            else
                dev = new MqttDeviceConnection();
            dev.SetListener(l);
        }
        return dev;
    }
}
