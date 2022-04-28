package com.jmj8257.crop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CropPlugin extends CordovaPlugin {
    private CallbackContext callbackContext;
    private Uri inputUri;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("cropImage")) {
            String imagePath = args.getString(0);
            JSONObject options = args.getJSONObject(1);
            int targetWidth = options.getInt("targetWidth");
            int targetHeight = options.getInt("targetHeight");
            boolean isSquare = options.getBoolean("square");

            this.inputUri = Uri.parse(imagePath);

            PluginResult pr = new PluginResult(PluginResult.Status.NO_RESULT);
            pr.setKeepCallback(true);
            callbackContext.sendPluginResult(pr);
            this.callbackContext = callbackContext;

            cordova.setActivityResultCallback(this);

            CropImage.activity(this.inputUri)
                    .setAllowRotation(true)
                    .setInitialRotation(90)
                    .setAutoZoomEnabled(true)
                    .setMultiTouchEnabled(true)
                    .setShowCropOverlay(true)
                    .setFixAspectRatio(isSquare)
                    .start(cordova.getActivity());
            return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(intent);
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                String path = resultUri.toString();
                this.callbackContext.success(path);
                this.callbackContext = null;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                try {
                    JSONObject err = new JSONObject();
                    err.put("message", error.getMessage());
                    err.put("code", String.valueOf(resultCode));
                    this.callbackContext.error(err);
                    this.callbackContext = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {

        if (state.containsKey("inputUri")) {
            this.inputUri = Uri.parse(state.getString("inputUri"));
        }

        if (state.containsKey("outputUri")) {
            this.inputUri = Uri.parse(state.getString("outputUri"));
        }

        this.callbackContext = callbackContext;
    }
}
