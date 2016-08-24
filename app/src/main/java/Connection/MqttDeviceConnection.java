package Connection;

import rest.IDeviceConnectionListener;
import Model.ObjectDevice;
import Model.ObjectPort;

public class MqttDeviceConnection implements IDeviceConnection {

    @Override
    public void SetListener(IDeviceConnectionListener listener) {

    }

    @Override
    public void GetStatus(long DeviceId, int count) {
    }

    @Override
    public void SetStatus(ObjectDevice device, ObjectPort key, boolean status) {

    }


}
