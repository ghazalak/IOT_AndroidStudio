package Connection;

import Model.ObjectColor;
import main.IDeviceConnectionListener;
import Model.ObjectDevice;
import Model.ObjectPort;

public interface IDeviceConnection {
    void SetListener(IDeviceConnectionListener listener);

    void GetValue(ObjectDevice device, ObjectPort port);

    void SetValue(ObjectDevice device, ObjectPort port, String value);
}
