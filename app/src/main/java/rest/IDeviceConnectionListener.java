package rest;

public interface IDeviceConnectionListener {
    void StatusChangedCallback(long deviceId, int keyIdx, boolean status);
}
