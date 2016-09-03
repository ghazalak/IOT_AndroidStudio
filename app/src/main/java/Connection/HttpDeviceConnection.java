package Connection;

import main.IDeviceConnectionListener;
import Model.ObjectDevice;
import Model.ObjectPort;

public class HttpDeviceConnection implements IDeviceConnection {
    IDeviceConnectionListener listener;
    @Override
    public void SetListener(IDeviceConnectionListener listener) {
        this.listener = listener;
    }

    @Override
    public void GetValue(ObjectDevice device, ObjectPort port) {
        try {
            new RequestTask(this, device.getId(), port.getIndex()).execute(port.getGetUrl(device));
        }
        catch(Exception e)
        {
//            Toast.makeText()
        }
    }

    @Override
    public void SetValue(ObjectDevice device, ObjectPort port, String value) {
        new RequestTask(this, device.getId(), port.getIndex()).execute(port.getSetUrl(device, value));
    }


    public void StatusChangedCallback(long DeviceId, int portIdx, String value) {
        if (listener != null)
            listener.StatusChangedCallback(DeviceId, portIdx, value);
    }

}