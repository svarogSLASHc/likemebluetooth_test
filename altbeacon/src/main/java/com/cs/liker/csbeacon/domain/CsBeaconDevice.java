package com.cs.liker.csbeacon.domain;

public class CsBeaconDevice {
    public static long REFRESH_TIME = 1000;
    public static long TIMEOUT_TIME = 4500;
    private String uuid;
    private String mac;
    private int rssi;
    private long timestamp;
    private boolean emited = false;
    private long connectingTime;
    private boolean connecting = false;
    private boolean likerDevice = true;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean wasEmited() {
        return emited;
    }

    public void setEmited(boolean connecting) {
        emited = connecting;
    }

    public long connectingTime() {
        return connectingTime;
    }

    public void setConnectingTime(long connectingTime) {
        this.connectingTime = connectingTime;
    }

    public boolean isConnecting() {
        return connecting;
    }

    public void setConnecting(boolean connecting) {
        this.connecting = connecting;
    }

    public boolean isConnectingTimeout(long currentTime){
        return  connectingTime() !=0 && currentTime - connectingTime() > CsBeaconDevice.TIMEOUT_TIME;
    }

    public boolean isLikerDevice() {
        return likerDevice;
    }

    public void setLikerDevice(boolean likerDevice) {
        this.likerDevice = likerDevice;
    }
}
