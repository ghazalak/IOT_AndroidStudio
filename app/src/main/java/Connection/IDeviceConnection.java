package Connection;

import rest.IDeviceConnectionListener;
import Model.ObjectDevice;
import Model.ObjectPort;

public interface IDeviceConnection {
    void SetListener(IDeviceConnectionListener listener);
    void GetStatus(long DeviceId, int count);
    void SetStatus(ObjectDevice device, ObjectPort key, boolean status);
}
