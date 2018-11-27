package fr.diginamic.formation.monquizz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public static final String NETWORK_CHANGE_ACTION = "com.androiderstack.broadcastreceiverdemo.NetworkChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOnline(context))
        {
            sendInternalBroadcast(context, "Internet Connected");
        }
        else
        {
            sendInternalBroadcast(context, "Internet Not Connected");
        }
    }

    private void sendInternalBroadcast(Context context, String status)
    {
        Log.e("BROADCAST_RECEIVED", String.valueOf(isOnline(context)));
        try
        {
            Intent intent = new Intent();
            boolean isOnline = isOnline(context);
            intent.putExtra("online",isOnline);
            intent.putExtra("status", status);
            intent.setAction(NETWORK_CHANGE_ACTION);
            context.sendBroadcast(intent);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean isOnline(Context context)
    {
        boolean isOnline = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            isOnline = (netInfo != null && netInfo.isConnected());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return isOnline;
    }
}
