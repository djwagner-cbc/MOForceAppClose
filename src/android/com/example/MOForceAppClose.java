/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import java.util.Date;
import java.util.List;
import android.util.Log;

public class MOForceAppClose extends CordovaPlugin {
  private static final String TAG = "MOForceAppClose";
  private CordovaWebView webView;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
	this.webView = webView;
    Log.d(TAG, "Initializing MOForceAppClose");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
	if(action.equals("forceAppClose")) {
		try { 
		    if (Build.VERSION.SDK_INT >= 21) {
 				ActivityManager am = (ActivityManager) webView.getContext().getSystemService(Context.ACTIVITY_SERVICE);
				if(am != null) {
					List<ActivityManager.AppTask> tasks = am.getAppTasks();
					if (tasks != null) {
						tasks.get(0).finishAndRemoveTask();
					}
				}
			} else {
				Activity activity = this.cordova.getActivity();
				activity.finish();
				System.exit(0);
			}		
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, 0)); 
		} catch (Exception e) { 
			callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, 1)); 
		} 
		return true; 
	} 
	return false; 
  }

}
