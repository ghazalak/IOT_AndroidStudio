package Connection;

import rest.IDeviceConnectionListener;
import Model.ObjectDevice;
import Model.ObjectPort;

public class HttpDeviceConnection implements IDeviceConnection {
    IDeviceConnectionListener listener;
    @Override
    public void SetListener(IDeviceConnectionListener listener) {
        this.listener = listener;
    }

    @Override
    public void GetStatus(long deviceId, int keyIdx) {
        new RequestTask(this,deviceId, keyIdx).execute("http://"+deviceId+"/get_status"+keyIdx);
    }

    @Override
    public void SetStatus(ObjectDevice device, ObjectPort key, boolean status) {
        new RequestTask(this, device.getId(), key.getIndex()).execute("http://" + device.getAddress() + "/key" + (key.getIndex()) + (status ? "_on" : "_off"));
    }

    public void StatusChangedCallback(long DeviceId, int KeyIdx, boolean status) {
        if (listener != null)
            listener.StatusChangedCallback(DeviceId, KeyIdx, status);
    }

}