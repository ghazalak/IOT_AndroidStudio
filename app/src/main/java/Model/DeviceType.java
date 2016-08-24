package Model;

public enum DeviceType {
    Key("Key", 0),
    Socket("Socket", 1);

    private String stringValue;
    private int intValue;
    private DeviceType(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
    public Integer toInteger(){
        return intValue;
    }
}