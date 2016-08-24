package Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import com.scurab.android.colorpicker.GradientView;
import com.scurab.android.colorpicker.MainActivity;

import Connection.DeviceConnectionFactory;
import Model.ObjectDevice;
import Model.ObjectGroup;
import Model.ObjectPort;
import rest.R;

import rest.IDeviceConnectionListener;
import rest.Models;
//import com.example.IOT.Models;
//import com.example.IOT.IDeviceConnectionListener;

public class ButtonsExpandableListAdapter extends BaseExpandableListAdapter implements IDeviceConnectionListener {
    private Context _context;
    View view;
    public ButtonsExpandableListAdapter(Context context, View view) {
        this._context = context;
        this.view = view;
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Models.Groups.get(groupPosition).getDevice(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    int getButtonById(int id)
    {
        int buttonId = 0;
        switch (id) {
            case 0:
                buttonId = R.id.buttons_Child1Row_button;
                break;
            case 1:
                buttonId = R.id.buttons_Child2Row_button;
                break;
            case 2:
                buttonId = R.id.buttons_Child3Row_button;
                break;
            case 3:
                buttonId = R.id.buttons_Child4Row_button;
                break;
        }
        return buttonId;
    }
    int getTextById(int id)
    {
        int buttonId = 0;
        switch (id) {
            case 0:
                buttonId = R.id.buttons_Child1Row_text;
                break;
            case 1:
                buttonId = R.id.buttons_Child2Row_text;
                break;
            case 2:
                buttonId = R.id.buttons_Child3Row_text;
                break;
            case 3:
                buttonId = R.id.buttons_Child4Row_text;
                break;
        }
        return buttonId;
    }
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final ObjectDevice device = Models.Groups.get(groupPosition).getDevice(childPosition);

        //if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (device.getPortsCount() == 4)
                convertView = infalInflater.inflate(R.layout.buttons_4childrow, null);
            else if (device.getPortsCount() == 3)
                convertView = infalInflater.inflate(R.layout.buttons_3childrow, null);
            else if (device.getPortsCount() == 2)
                convertView = infalInflater.inflate(R.layout.buttons_2childrow, null);
            else if (device.getPortsCount() == 1)
                convertView = infalInflater.inflate(R.layout.buttons_1childrow, null);
        //}
        int i;
        for (i = 0; i < device.getPortsCount(); i++) {
            final ObjectPort key = device.getKey(i);
            final Button btn = (Button) convertView.findViewById(getButtonById(i));
            final TextView text = (TextView) convertView.findViewById(getTextById(i));
            btn.setTag(key);
//            btn.setText(key.getName());
            text.setText(key.getName());
            btn.setOnClickListener(null);
            final IDeviceConnectionListener thisListener = this;

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ObjectPort keyTag = (ObjectPort) view.getTag();
                    DeviceConnectionFactory.GetDeviceConnection(thisListener).SetStatus(keyTag.getDevice(), keyTag, !keyTag.getStatus());

                    if (device.getTypeDevice() == 3) {
                        LayoutInflater li = LayoutInflater.from(_context);
                        final View promptsView = li.inflate(R.layout.activity_main, null);
                        final GradientView mTop;
                        GradientView mBottom;
                        final TextView mTextView;
                        Drawable mIcon;
                        mIcon = promptsView.getResources().getDrawable(R.mipmap.ic_launcher);
                        mTextView = (TextView)promptsView.findViewById(R.id.color);
                        mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
                        mTop = (GradientView)promptsView.findViewById(R.id.top);
                        mBottom = (GradientView)promptsView.findViewById(R.id.bottom);
                        mTop.setBrightnessGradientView(mBottom);
                        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
                            @Override
                            public void onColorChanged(GradientView view, int color) {
                                mTextView.setTextColor(color);
                                mTextView.setText("#" + Integer.toHexString(color));
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    mIcon.setTint(color);
//                }
                            }
                        });

                        int color = 0xFF394572;
                        mTop.setColor(color);
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(_context);
                        dlgAlert.setView(promptsView);
                        dlgAlert.setCancelable(false).setPositiveButton("تایید",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {
                                        btn.setBackgroundColor(Color.parseColor(mTextView.getText().toString()));
                                    }
                                })
                                .setNegativeButton("بازگشت",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                btn.setBackgroundColor(Color.parseColor(mTextView.getText().toString()));
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertDialog = dlgAlert.create();
                        alertDialog.show();
                    }
                }
            });
            if (key.getStatus() == true && device.getTypeDevice() == 2)
                btn.setBackgroundResource(R.drawable.socket_on);
//                btn.setBackgroundColor( Color.parseColor("#FF11AC06"));
            else if (key.getStatus() == true && device.getTypeDevice() == 1)
                btn.setBackgroundResource(R.drawable.key_on);
            else if (key.getStatus() == false && device.getTypeDevice() == 1)
                btn.setBackgroundResource(R.drawable.key_off);
            else if (device.getTypeDevice() == 3) {
                btn.setBackgroundColor(Color.parseColor("#FF11AC06"));
            }
            else
                btn.setBackgroundResource(R.drawable.socket_off);
//            btn.setBackgroundColor(Color.parseColor("#FF333333"));
        }
        TextView deviceName = (TextView) convertView.findViewById(R.id.deviceName);
        deviceName.setText(device.getName());


//        for(int j=i; j<4;j++){
//            convertView.findViewById(getButtonById(j)).setVisibility(View.GONE);
//        }
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
        //if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.buttons_parentrow, null);
        //}
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
    public void StatusChangedCallback(long  deviceId,int keyIdx, boolean status) {
        Models.getDeviceById(deviceId).getKeyByIndex(keyIdx).setStatus(status);
        notifyDataSetChanged();
    }
}