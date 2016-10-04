package main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import Connection.RequestTask;

public class Connect extends Activity {
    private int MOBILE_WIRELESS_CONNECTION_TIMEOUT = 10000;
    private int DEVICE_WIRELESS_CONNECTION_TIMEOUT = 3000;
    private int BROADCAST_SUMMARY_RECEIVE_TIMEOUT = 10000;
    private int BROADCAST_SINGLE_RECEIVE_TIMEOUT = 1000;

    String BEH_KEY = "beh-key";
    String BEHKEY_PASS = "123456789";
    String NETWORK_SSID = "sara";
    String NETWORK_PASS = "3390377475";

    Context context1;
    WifiReceiver wifiReceiver;
//    public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "main.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";

    private class WifiReceiver extends BroadcastReceiver {
        boolean done = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!done)
            {
                unregisterReceiver(wifiReceiver);
                done = true;
                connectAndConfigureWifi();
                broadcastMessage();
                runOnUiThread(finish);
            }
        }
    }

    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);
        context1 = getApplicationContext();
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }
    public Runnable finish = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };
    private void connectAndConfigureWifi() {
        try {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi;
            List<ScanResult> results = wifiManager.getScanResults();
            int foundedDevices = 0;
            WifiConfiguration conf;
            for (ScanResult scanResult : results) {
                if (scanResult.SSID != null && scanResult.SSID.toLowerCase().contains(BEH_KEY.toLowerCase())) {

                    conf = new WifiConfiguration();
                    conf.SSID = "\"" + scanResult.SSID + "\"";   // Please note the quotes. String should contain SSID in quotes
                    conf.preSharedKey = "\"" + BEHKEY_PASS + "\"";
                    conf.status = WifiConfiguration.Status.ENABLED;
                    conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                    conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    int netId = wifiManager.addNetwork(conf);
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.setWifiEnabled(true);

                    mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    int tryCount = 0;
                    while (!mWifi.isConnected() && tryCount < 10) {
                        Thread.sleep(100);
                        tryCount++;
                    }
                    Thread.sleep(DEVICE_WIRELESS_CONNECTION_TIMEOUT);
                    new RequestTask().execute("http://192.168.1.1/setWiFi?modem_ssid="+ NETWORK_SSID +"&modem_password="+ NETWORK_PASS +"&behpooyesh_password=12345");
                    foundedDevices++;
                    Log.d("re connecting", scanResult.SSID + " " + conf.preSharedKey);
                }
            }
            final int finalFoundedDevices = foundedDevices;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Connect.this, String.valueOf(finalFoundedDevices) + " دستگاه اضافه شد", Toast.LENGTH_LONG).show();
                }
            });

            conf = new WifiConfiguration();
            conf.SSID = "\"" + NETWORK_SSID + "\"";//"\"" + "Beh-Mobin" + "\"";   // Please note the quotes. String should contain SSID in quotes
            conf.preSharedKey = "\"" + NETWORK_PASS + "\"";//"\"55243098\"";
            conf.status = WifiConfiguration.Status.ENABLED;
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            int netId = wifiManager.addNetwork(conf);
            wifiManager.enableNetwork(netId, true);
            wifiManager.setWifiEnabled(true);
            Thread.sleep(MOBILE_WIRELESS_CONNECTION_TIMEOUT);
//            finish();
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Connect.this, "ERROR...", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void broadcastMessage() {
        DatagramSocket socketReceive = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DatagramSocket socket = null;
        try {
            //Open a random port to send the package
            socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] sendData = "who are u?".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, getBroadcastAddress(), 8888);
            socket.send(sendPacket);
            System.out.println(getClass().getName() + "Broadcast packet sent to: " + getBroadcastAddress().getHostAddress());
        } catch (IOException e) {
            Log.e("123", "IOException: " + e.getMessage());
        }
        finally
        {
            if(socket != null)
                socket.close();
        }

        try {
            socketReceive = new DatagramSocket(9999, InetAddress.getByName("0.0.0.0"));

            long startTime = System.currentTimeMillis();
            long totalTime = 0;
            long endTime;
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            while ( totalTime < BROADCAST_SUMMARY_RECEIVE_TIMEOUT) {
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                Log.i("123", "Ready to receive broadcast packets!");

                //Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socketReceive.setSoTimeout(BROADCAST_SINGLE_RECEIVE_TIMEOUT);
                try {
                    socketReceive.receive(packet);
                    Log.i("123", "Packet received from: " + packet.getAddress().getHostAddress());
                    String data = new String(packet.getData()).trim();
                    Log.i("123", "Packet received; data: " + data);
                }
                catch (SocketTimeoutException e) {
                    // timeout exception.
                    System.out.println("Timeout reached!!! " + e);
                    //socketReceive.close();
                }
            }
        } catch (IOException ex) {
            Log.i("123", "Oops" + ex.getMessage());
        }
        finally
        {
            if(socketReceive != null)
                socketReceive.close();
        }
    }

    InetAddress getBroadcastAddress() throws IOException {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }
}