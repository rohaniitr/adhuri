package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;

import in.rohansarkar.adhuri.Model.Repositories.UserModel;

import static in.rohansarkar.adhuri.Util.Util.REQUEST_GET_IMAGE;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Boolean> imageDownloaded;
    private MutableLiveData<String> toastMessage;
    private UserModel userModel;

    //Get User Image
    public void downloadImage(Context context, String imageName, String token){
        if(imageName==null || imageName.length() <= 0)
            return;

        if(userModel == null)
            userModel = new UserModel();
        userModel.downloadImage(context, imageName, token, toastMessage, imageDownloaded);
    }

    public MutableLiveData<Boolean> getImageDownloaded() {
        if(imageDownloaded == null)
            imageDownloaded = new MutableLiveData<>();
        return imageDownloaded;
    }
    public MutableLiveData<String> getToastMessage() {
        if(toastMessage == null)
            toastMessage = new MutableLiveData<>();
        return toastMessage;
    }
}
