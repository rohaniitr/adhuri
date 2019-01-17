package in.rohansarkar.adhuri.View.Utils;

import android.os.Parcelable;

import java.io.Serializable;

public interface CustomAlertAction extends Serializable {
    public void onSuccess();
    public void onError();
}
