package com.test.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/** created by leo on 2017.04.27**/
public class Netword {
	private static Activity INSTANCE;
	private static ConnectivityManager manager;
	
	public static void init(Activity context) {
		INSTANCE=context;
		//获取手机所有连接管理对象(包括对wi-fi,net等连接的管理) 
		manager = (ConnectivityManager) INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE);

	}
	
	//是否连接网络
	public static boolean isNetworkConnected() {
		if(INSTANCE==null) {
			return false;
		}
		//  获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //判断NetworkInfo对象是否为空
        if (networkInfo != null) {
        	return networkInfo.isAvailable(); 
        }
		return false;
	}
	
	//获取当前的网络状态 ：没有网络-0：WIFI网络1：移动网络为2
	public static int getNetType() {
		int netType=0;
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		if(networkInfo==null) {
			return netType;
		}
		
		//否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
//        	int nSubType = networkInfo.getSubtype();
        	netType=2;
        }
		return netType;
	}
	
	public static void register() {
		
		IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        INSTANCE.registerReceiver(netReceiver, itFilter);
	}
	
	public static void unregister() {
		INSTANCE.unregisterReceiver(netReceiver);
	}
	
	private static BroadcastReceiver netReceiver= new BroadcastReceiver(){

		@Override
		public void onReceive(Context arg0, Intent arg1) {
//			if(isNetworkConnected()) {
//				Toast.makeText(INSTANCE,"网络已连接 ~"+arg1.toString(),Toast.LENGTH_SHORT).show();
//			}else {
//				Toast.makeText(INSTANCE,"网络已断开~ "+arg1.toString(),Toast.LENGTH_SHORT).show();
//			}
			
			switch(getNetType()) {
				case 0:Toast.makeText(INSTANCE,"无网络 ~",Toast.LENGTH_SHORT).show();break;
				case 1:Toast.makeText(INSTANCE,"wifi~",Toast.LENGTH_SHORT).show();break;
				case 2:Toast.makeText(INSTANCE,"移动网络 ~",Toast.LENGTH_SHORT).show();break;
			}
		}
		
	};

}
