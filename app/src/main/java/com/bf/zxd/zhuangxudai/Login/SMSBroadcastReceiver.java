package com.bf.zxd.zhuangxudai.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by johe on 2017/2/17.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("gqf", "SMSBroadcastReceiver");
        if (intent.getAction().equals(RegistActivity.SMS_ACTION)) {
            Log.i("gqf", "SMSBroadcastReceiver");
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            for (Object p : pdus) {
                byte[] pdu = (byte[]) p;
                SmsMessage message = SmsMessage.createFromPdu(pdu);
                String content = message.getMessageBody();
                Date date = new Date(message.getTimestampMillis());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                String senderNumber = message.getOriginatingAddress();
                //sendSMS(content, receiveTime, senderNumber);

                Log.i("gqf", senderNumber + "SMSBroadcastReceiver" + content);
                if ("5556".equals(senderNumber)) {
                    abortBroadcast();//终止广播
                }
            }
        }
    }
}