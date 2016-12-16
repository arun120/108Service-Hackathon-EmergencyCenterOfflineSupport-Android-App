package com.b2b.home.servicecenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    public SMSReceiver() {
    }
    final SmsManager sms = SmsManager.getDefault();
    @Override
    public void onReceive(Context context, Intent intent) {



        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();


                    Intent intent1=new Intent(context,SMSHandler.class);
                    message=message.substring(message.indexOf("http"));
           //         message=message.substring(0,message.indexOf(' '));

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    intent1.putExtra("msg",message);
                    context.startService(intent1);



                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


}

