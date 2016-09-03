package main;

public interface IDeviceConnectionListener {
    void StatusChangedCallback(long deviceId, int keyIdx, String status);

}
