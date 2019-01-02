package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;

import in.rohansarkar.adhuri.Model.Data.UserData;
import in.rohansarkar.adhuri.Model.Repositories.UserModel;

import static in.rohansarkar.adhuri.Util.Util.REQUEST_GET_IMAGE;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<Boolean> imageDownloaded;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<String> loadingMessage;
    private MutableLiveData<UserData> userData;
    private MutableLiveData<String> toastMessage;
    private UserModel userModel;

    //Get User Profile
    public void getUserInfo(String userId, String token){
        if(userId==null || token==null)
            return;

        if(userModel == null)
            userModel = new UserModel();
        userModel.getUserInfo(userId, token, userData, showLoading, loadingMessage, toastMessage);
    }
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
    public MutableLiveData<String> getLoadingMessage() {
        if(loadingMessage == null)
            loadingMessage = new MutableLiveData<>();
        return loadingMessage;
    }
    public MutableLiveData<String> getToastMessage() {
        if(toastMessage == null)
            toastMessage = new MutableLiveData<>();
        return toastMessage;
    }
    public MutableLiveData<Boolean> getShowLoading() {
        if(showLoading==null)
            showLoading = new MutableLiveData<>();
        return showLoading;
    }
    public MutableLiveData<UserData> getUserData() {
        if(userData==null)
            userData = new MutableLiveData<>();
        return userData;
    }
}
