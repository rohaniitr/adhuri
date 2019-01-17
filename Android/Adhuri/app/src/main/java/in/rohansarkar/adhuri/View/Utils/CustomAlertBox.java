package in.rohansarkar.adhuri.View.Utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;

import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Activity.VerificationActivity;

public class CustomAlertBox extends DialogFragment {
    private String LOG_TAG = this.getClass().getName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if(bundle==null){
            Log.e(LOG_TAG, "this.getArguments() is NULL");
            return null;
        }

        int titleId = bundle.getInt(getString(R.string.alert_title_id));
        int iconId = bundle.getInt(getString(R.string.alert_icon_id));
        int messageId = bundle.getInt(getString(R.string.alert_message_id));
        final CustomAlertAction alertAction = (CustomAlertAction) bundle.getSerializable(getString(R.string.alert_action_object));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titleId).setIcon(iconId)
                .setMessage(messageId)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertAction.onSuccess();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do Nothing
                        alertAction.onError();
                    }
                });

        return builder.create();
    }

}