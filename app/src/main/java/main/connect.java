package main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;
import Connection.RequestTask;

public class Connect extends Activity {

    WifiReceiver wifiReceiver;

    private class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            afterCreate();
            // TODO: Finish Me
            return;
        }
    }

    protected void onPause() {
        unregisterReceiver(wifiReceiver);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiReceiver = new WifiReceiver();
        wifiManager.startScan();
    }

    private void afterCreate() {
        setContentView(R.layout.connect);
//        String networkSSID = "Beh-Mobin";
//        String networkPass = "\"55243098\"";
        String networkSSID = "Beh-Key";
        String networkPass = "\"123456789\"";
        try {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi;
            List<ScanResult> results = wifiManager.getScanResults();
            int foundedDevices = 0;
            for (ScanResult scanResult : results) {
                if (scanResult.SSID != null && scanResult.SSID.toLowerCase().contains(networkSSID.toLowerCase())) {

                    WifiConfiguration conf = new WifiConfiguration();
                    conf.SSID = "\"" + scanResult.SSID + "\"";   // Please note the quotes. String should contain SSID in quotes
                    conf.preSharedKey = networkPass;
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
                    Thread.sleep(3000);
                    new RequestTask().execute("http://192.168.1.1/setWiFi?modem_ssid=Beh-Mobin&modem_password=55243098&behpooyesh_password=12345");
                    foundedDevices++;
                    Log.d("re connecting", scanResult.SSID + " " + conf.preSharedKey);
                }
            }

            Toast.makeText(this, String.valueOf(foundedDevices) + " دستگاه اضافه شد", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            Toast.makeText(this, "ERROR...", Toast.LENGTH_LONG).show();
            finish();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            //Open a random port to send the package
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] sendData = "who are u?".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.0.102"), 8888);

            socket.send(sendPacket);
            System.out.println(getClass().getName() + "Broadcast packet sent to: " + getBroadcastAddress().getHostAddress());
        } catch (IOException e) {
            Log.e("123", "IOException: " + e.getMessage());
        }


        DatagramSocket socket;
        try {
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            socket = new DatagramSocket(9999, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (true) {
                Log.i("123","Ready to receive broadcast packets!");

                //Receive a packet
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                //Packet received
                Log.i("123", "Packet received from: " + packet.getAddress().getHostAddress());
                String data = new String(packet.getData()).trim();
                Log.i("123", "Packet received; data: " + data);

                // Send the packet data back to the UI thread
//                Intent localIntent = new Intent(SyncStateContract.Constants.BROADCAST_ACTION)
//                        // Puts the data into the Intent
//                        .putExtra(SyncStateContract.Constants.EXTENDED_DATA_STATUS, data);
//                // Broadcasts the Intent to receivers in this app.
//                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            }
        } catch (IOException ex) {
            Log.i("123", "Oops" + ex.getMessage());
        }



    }


    InetAddress getBroadcastAddress() throws IOException {
        WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }
}
