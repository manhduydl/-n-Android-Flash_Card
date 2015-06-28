package com.example.duy.flashcard_v10.fragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duy.flashcard_v10.AlarmReceiver;
import com.example.duy.flashcard_v10.Helper;
import com.example.duy.flashcard_v10.R;
import com.example.duy.flashcard_v10.info_parcel;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by duy on 22/06/15.
 */
public class ScrollFragment extends Fragment implements View.OnClickListener{

    Button btn_start = null;
    Button btn_stop = null;
    Button btn_startat = null;
    Button btn_save;
    CheckBox ck;
    TextView tv_start_time;
    TextView tv_interval;
    EditText txtword;
    EditText txtmean;
    boolean checked;
    int interval;
    String start_time;
    private Helper helper;

    private PendingIntent pendingIntent;

    private ObservableScrollView mScrollView;

    public static ScrollFragment newInstance() {
        return new ScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       /* Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);*/
        View rootView= inflater.inflate(R.layout.fragment_scroll, container, false);
        // Initilize database
        helper = new Helper(getActivity());

        //thao tác button
        btn_start= (Button) rootView.findViewById(R.id.btn_start);
        btn_stop = (Button) rootView.findViewById(R.id.btn_stop);
        btn_save = (Button)rootView.findViewById(R.id.btn_save);
        btn_startat = (Button)rootView.findViewById(R.id.btn_startat);
        btn_startat.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        //khai báo Preference
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preference, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //kiểm tra các biến setting
        checked= sharedPref.getBoolean("pref_noti", true);
        interval= sharedPref.getInt("pref_number", 5);
        start_time= sharedPref.getString("pref_start_time", "10:10");

        //hiển thi textview
        tv_start_time= (TextView) rootView.findViewById(R.id.tv_start_time);
        tv_interval= (TextView) rootView.findViewById(R.id.tv_interval);
        tv_start_time.setText(start_time);
        tv_interval.setText(String.valueOf(interval));
        txtword = (EditText)rootView.findViewById(R.id.edttxt_word);
        txtmean = (EditText)rootView.findViewById(R.id.edttxt_mean);
        ck= (CheckBox) rootView.findViewById(R.id.ckb_addNoti);
        ck.setChecked(true);

        if(!checked){
            btn_start.setEnabled(false);
            btn_stop.setEnabled(false);
            btn_startat.setEnabled(false);
        }else {
            btn_start.setEnabled(true);
            btn_stop.setEnabled(true);
            btn_startat.setEnabled(true);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);

        AddData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //cập nhật lại biến setting
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        checked= sharedPref.getBoolean("pref_noti", true);
        interval= sharedPref.getInt("pref_number", 5);
        start_time= sharedPref.getString("pref_start_time", "10:10");

        //kiểm tra xem người dùng có cấm quyền notification hay không.
        if(!checked){
            btn_start.setEnabled(false);
            btn_stop.setEnabled(false);
            btn_startat.setEnabled(false);
        }else {
            btn_start.setEnabled(true);
            btn_stop.setEnabled(true);
            btn_startat.setEnabled(true);
        }

        tv_start_time.setText(start_time);
        tv_interval.setText(String.valueOf(interval));
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.btn_start:
                startAlarm(interval*1000);
                break;
            case R.id.btn_stop:
                stopAlarm();
                break;
            case R.id.btn_startat:
                startAlarmat(start_time,interval*1000);
                break;
            default:
                break;
        }
    }// Alert button

    private ArrayList<info_parcel> mContentItems = new ArrayList<>();
    public void getWord(){
        Cursor res = helper.GetAllData();
        mContentItems.clear();
        if(res.getCount() == 0) {
            //mContentItems.add(new info_parcel("Create Word","",0,"",0));
        }
        else {
            while (res.moveToNext()) {
                if(res.getInt(4)==1) {
                    mContentItems.add(new info_parcel(res.getString(1)
                            , res.getString(2)
                            , 0
                            , res.getString(0)
                            , Integer.parseInt(res.getString(4))));
                }
            }
        }
    }

    public void startAlarm(int interval){
        getWord();
        if(mContentItems.size()==0){//không có từ nào được set noti
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning")
                    .setMessage("You don't have word to show. Please add word to Noti group to show in notification")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else {
            //sử dụng Parcelable để chuyển list object qua Broadcast receiver
            Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
            alarmIntent.putParcelableArrayListExtra("list_word", mContentItems);
            pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval, interval, pendingIntent);
            Toast.makeText(getActivity(), "Alarm Set", Toast.LENGTH_SHORT).show();
        }
    }

    public void startAlarmat(String Time, Integer interval){
        getWord();
        if(mContentItems.size()==0){//không có từ nào được set noti
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning")
                    .setMessage("You don't have word to show. Please add word to Noti group to show in notification")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else {
            //sử dụng Parcelable để chuyển list object qua Broadcast receiver
            Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
            alarmIntent.putParcelableArrayListExtra("list_word", mContentItems);
            pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
            AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
             /* Set thời gian bắt đầu  */
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, getHour(Time));
            calendar.set(Calendar.MINUTE, getMinute(Time));

            Calendar c= Calendar.getInstance();
            if(getHour(Time)<c.get(Calendar.HOUR_OF_DAY) || (getHour(Time)==c.get(Calendar.HOUR_OF_DAY) && getMinute(Time)<c.get(Calendar.MINUTE))){
                Toast.makeText(getActivity(),"Please select start time after now" , Toast.LENGTH_SHORT).show();
            }
            else {
                /* Set thời gian lặp lại*/
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        interval, pendingIntent);
                Toast.makeText(getActivity(), "Alarm will start at" + Time, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void stopAlarm(){
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
        Toast.makeText(getActivity(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {

        }
    }// Update fragment

    public void AddData()
    {
        btn_save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg;
                        String Word = txtword.getText().toString();
                        String Meaning = txtmean.getText().toString();

                        if (ck.isChecked()) {
                            msg = helper.insertData(Word, Meaning, 1);
                        } else {
                            msg = helper.insertData(Word, Meaning, 0);
                        }
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }// Add Data

    //hàm chuyển đổi giờ kiểu string sang int
    public static int getHour(String time) {
        String[] pieces = time.split(":");
        return Integer.parseInt(pieces[0]);
    }

    public static int getMinute(String time) {
        String[] pieces = time.split(":");
        return Integer.parseInt(pieces[1]);
    }
}
