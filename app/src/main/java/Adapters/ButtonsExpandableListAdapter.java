package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.TextView;
import com.scurab.android.colorpicker.GradientView;
import Connection.DeviceConnectionFactory;
import Model.ObjectDevice;
import Model.ObjectGroup;
import Model.ObjectPort;
import Model.ObjectPorts.ObjectPortDimmer;
import Model.ObjectPorts.ObjectPortKey;
import Model.ObjectPorts.ObjectPortLedRgb;
import Model.ObjectPorts.ObjectPortSocket;

import main.CircularSeekBar;
import main.R;
import main.IDeviceConnectionListener;
import main.Models;

public class ButtonsExpandableListAdapter extends BaseExpandableListAdapter implements IDeviceConnectionListener {
    private Context _context;
    View view;
    public ButtonsExpandableListAdapter(Context context, View view) {
        this._context = context;
        this.view = view;
        for (ObjectGroup group:Models.Groups) {
            for (ObjectDevice device:group.getDevices()) {
                for (ObjectPort port:device.getPorts()) {
                    DeviceConnectionFactory.GetDeviceConnection(this).GetValue(device, port);
                }
            }
        }

    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Models.Groups.get(groupPosition).getDevice(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    int getButtonByIndex(int index)
    {
        int buttonId = 0;
        switch (index) {
            case 1:
                buttonId = R.id.buttons_Child1Row_button;
                break;
            case 2:
                buttonId = R.id.buttons_Child2Row_button;
                break;
            case 3:
                buttonId = R.id.buttons_Child3Row_button;
                break;
            case 4:
                buttonId = R.id.buttons_Child4Row_button;
                break;
        }
        return buttonId;
    }
    int getTextByIndex(int index)
    {
        int buttonId = 0;
        switch (index) {
            case 1:
                buttonId = R.id.buttons_Child1Row_text;
                break;
            case 2:
                buttonId = R.id.buttons_Child2Row_text;
                break;
            case 3:
                buttonId = R.id.buttons_Child3Row_text;
                break;
            case 4:
                buttonId = R.id.buttons_Child4Row_text;
                break;
        }
        return buttonId;
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ObjectDevice device = Models.Groups.get(groupPosition).getDevice(childPosition);

        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (device.getPortsCount() == 4)
            convertView = infalInflater.inflate(R.layout.buttons_4childrow, null);
        else if (device.getPortsCount() == 3)
            convertView = infalInflater.inflate(R.layout.buttons_3childrow, null);
        else if (device.getPortsCount() == 2)
            convertView = infalInflater.inflate(R.layout.buttons_2childrow, null);
        else if (device.getPortsCount() == 1)
            convertView = infalInflater.inflate(R.layout.buttons_1childrow, null);

        for (int i = 1; i <= device.getPortsCount(); i++) {
            final ObjectPort port = device.getPortByIndex(i);
            final Button btn = (Button) convertView.findViewById(getButtonByIndex(i));
            final TextView text = (TextView) convertView.findViewById(getTextByIndex(i));
            btn.setTag(port);
            text.setText(port.getName());
            btn.setOnClickListener(null);
            final IDeviceConnectionListener thisListener = this;

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    LayoutInflater li = LayoutInflater.from(_context);

                    switch (device.getType()) {
                        case ObjectDevice.DEVICE_TYPE_DIMMER:
                            final ObjectPortDimmer dimmer = (ObjectPortDimmer) view.getTag();
                            final View promptsViewDimmer = li.inflate(R.layout.circular_seekbar, null);

                            final CircularSeekBar circularSeekbar;
                            circularSeekbar = (main.CircularSeekBar) promptsViewDimmer.findViewById(R.id.seekbar);
                            circularSeekbar.setProgress(dimmer.getValue());
                            circularSeekbar.invalidate();
                            circularSeekbar.setMax(100);
                            circularSeekbar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
                                    int base = circularSeekBar.getProgress();
                                    circularSeekBar.setCircleProgressColor(Color.argb(255,  base * 2,(int)(base * .2), (int)(base * .2)));
                                }

                                @Override
                                public void onStopTrackingTouch(CircularSeekBar seekBar) {
                                }

                                @Override
                                public void onStartTrackingTouch(CircularSeekBar seekBar) {
                                }
                            });
                            AlertDialog.Builder dlgAlertDimmer = new AlertDialog.Builder(_context);
                            dlgAlertDimmer.setView(promptsViewDimmer);
                            dlgAlertDimmer.setCancelable(false).setPositiveButton("تایید",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {

                                            DeviceConnectionFactory.GetDeviceConnection(thisListener).SetValue(dimmer.getDevice(), dimmer, String.valueOf(circularSeekbar.getProgress()));

                                        }
                                    })
                                    .setNegativeButton("بازگشت",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alertDialogDimmer = dlgAlertDimmer.create();
                            alertDialogDimmer.show();

                            break;
                        case ObjectDevice.DEVICE_TYPE_SOCKET:
                            ObjectPortSocket socket = (ObjectPortSocket) view.getTag();
                            DeviceConnectionFactory.GetDeviceConnection(thisListener).SetValue(socket.getDevice(), socket, String.valueOf(socket.getStatus()));
                            break;
                        case ObjectDevice.DEVICE_TYPE_LED_RGB:
                            final ObjectPortLedRgb ledRgb = (ObjectPortLedRgb) view.getTag();
                            final View promptsView = li.inflate(R.layout.activity_main, null);
                            final GradientView mTop;
                            GradientView mBottom;
                            final TextView mTextView;
                            Drawable mIcon;
                            mIcon = promptsView.getResources().getDrawable(R.mipmap.ic_launcher);
                            mTextView = (TextView) promptsView.findViewById(R.id.color);
                            mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
                            mTop = (GradientView) promptsView.findViewById(R.id.top);
                            mBottom = (GradientView) promptsView.findViewById(R.id.bottom);
                            mTop.setBrightnessGradientView(mBottom);
                            mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
                                @Override
                                public void onColorChanged(GradientView view, int color) {
                                    mTextView.setTextColor(color);
                                    mTextView.setText("#" + Integer.toHexString(color));
                                }
                            });

                            int color = 0xFF394572;
                            mTop.setColor(color);
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(_context);
                            dlgAlert.setView(promptsView);
                            dlgAlert.setCancelable(false).setPositiveButton("تایید",
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int id) {
                                            //btn.setBackgroundColor(Color.parseColor(mTextView.getText().toString()));
                                            DeviceConnectionFactory.GetDeviceConnection(thisListener).SetValue(ledRgb.getDevice(), ledRgb, mTextView.getText().toString());

                                        }
                                    })
                                    .setNegativeButton("بازگشت",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alertDialog = dlgAlert.create();
                            alertDialog.show();
                            break;
                        case ObjectDevice.DEVICE_TYPE_KEY:
                            ObjectPortKey key = (ObjectPortKey) view.getTag();
                            DeviceConnectionFactory.GetDeviceConnection(thisListener).SetValue(key.getDevice(), key, String.valueOf(key.getStatus()));
                            break;

                    }
                }
            });
            switch (device.getType()) {
                case ObjectDevice.DEVICE_TYPE_DIMMER:
                    btn.setBackgroundResource(R.drawable.led_silver_dimmable);
                    break;
                case ObjectDevice.DEVICE_TYPE_KEY:
                    ObjectPortKey key = (ObjectPortKey) port;
                    if (key.getStatus() == true)
                        btn.setBackgroundResource(R.drawable.key_on);
                    else if (key.getStatus() == false)
                        btn.setBackgroundResource(R.drawable.key_off);
                    break;
                case ObjectDevice.DEVICE_TYPE_LED_RGB:
                    ObjectPortLedRgb led = (ObjectPortLedRgb) port;
                    btn.setBackgroundColor(Color.argb(255, led.getRed(), led.getGreen(), led.getBlue()));
                    //Color.parseColor("#FF11AC06"));
                    break;
                case ObjectDevice.DEVICE_TYPE_SOCKET:
                    ObjectPortSocket socket = (ObjectPortSocket) port;
                    if (socket.getStatus() == true)
                        btn.setBackgroundResource(R.drawable.socket_on);
                    else if (socket.getStatus() == false)
                        btn.setBackgroundResource(R.drawable.socket_off);

                    break;

            }
        }

        TextView deviceName = (TextView) convertView.findViewById(R.id.deviceName);
        deviceName.setText(device.getName());

        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return Models.Groups.get(groupPosition).getDeviceCount();
    }
    @Override
    public Object getGroup(int groupPosition) {
        return Models.Groups.get(groupPosition);
    }
    @Override
    public int getGroupCount() { return Models.Groups.size(); }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

//        ExpandableListView eLV = (ExpandableListView) parent;
//        eLV.expandGroup(groupPosition);
//        ((ExpandableListView) parent).expandGroup(groupPosition);
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.buttons_parentrow, null);
        String headerTitle = ((ObjectGroup) getGroup(groupPosition)).getName();
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.buttons_parentRow_textview);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        return convertView;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void StatusChangedCallback(long  deviceId,int portIdx, String status) {
        if (status == null || status.isEmpty())
            return;

        ObjectDevice device = Models.getDeviceById(deviceId);
        ObjectPort port = device.getPortByIndex(portIdx);
        switch(device.getType())
        {
            case ObjectDevice.DEVICE_TYPE_DIMMER:
                port.setValue(status);
                break;
            case ObjectDevice.DEVICE_TYPE_KEY:
                port.setValue(status);
                break;
            case ObjectDevice.DEVICE_TYPE_LED_RGB:
                port.setValue(status);
                break;
            case ObjectDevice.DEVICE_TYPE_SOCKET:
                port.setValue(status);
                break;

        }

        notifyDataSetChanged();
    }


}