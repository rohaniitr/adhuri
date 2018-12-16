package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import in.rohansarkar.adhuri.Model.Repositories.UserModel;

import static in.rohansarkar.adhuri.Util.Util.REQUEST_GET_IMAGE;

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<Boolean> goToLogin;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<Boolean> imageChanged;
    private MutableLiveData<Boolean> userInfoUpadated;
    private MutableLiveData<String> loadingTitle;
    private MutableLiveData<String> toastMessage;
    private MutableLiveData<Boolean> removeUserInfo;
    private UserModel userModel;

    //Updates basic user details in Server
    public void saveUserInfo(String name, String gender, String bio, String token){
        if(name.length() < 0){
            toastMessage.setValue("Please enter a valid name");
            return;
        }

        if(userModel == null)
            userModel = new UserModel();
        userModel.saveUserInfo(name, gender, bio, token, showLoading, loadingTitle, toastMessage, userInfoUpadated);
    }
    //Saves image as user image in Server & locally as well
    public void uploadImage(Context context, Bitmap imageBitmap, String token){
        if(imageBitmap == null)
            toastMessage.setValue("Can't get the image. Please select another image.");

        if(userModel == null)
            userModel = new UserModel();
        userModel.uploadImage(context, imageBitmap, token, showLoading, loadingTitle, toastMessage, imageChanged);
    }
    //Deletes User details and moves to Login Fragment
    public void logout(){
        removeUserInfo.setValue(true);
        goToLogin.setValue(true);
    }
    //Deletes User account from server and moves to Login Fragment
    public void deleteAccount(){
        if(userModel == null)
            userModel = new UserModel();
        userModel.deleteAccount(showLoading, loadingTitle, toastMessage, removeUserInfo ,goToLogin);
    }
    //Invokes activity to get cropped image
    public void getImage(Fragment fragmentObj){
//        #####################################################################
//        Need to implement this for devices with NO GALELRY application
//        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        getIntent.setType("image/*");

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 10);
        intent.putExtra("aspectY", 10);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);

//        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        try {
            intent.putExtra("return-data", true);
            fragmentObj.startActivityForResult(Intent.createChooser(intent, "Choose Action Using"), REQUEST_GET_IMAGE);
        } catch (ActivityNotFoundException e) {
            toastMessage.setValue("Fuctionality unsupported");
            e.printStackTrace();
        }
    }

    public MutableLiveData<Boolean> getShowLoading() {
        if(showLoading == null)
            showLoading = new MutableLiveData<>();
        return showLoading;
    }
    public MutableLiveData<String> getToastMessage() {
        if(toastMessage == null)
            toastMessage = new MutableLiveData<>();
        return toastMessage;
    }
    public MutableLiveData<String> getLoadingTitle() {
        if(loadingTitle == null)
            loadingTitle = new MutableLiveData<>();
        return loadingTitle;
    }
    public MutableLiveData<Boolean> getGoToLogin() {
        if(goToLogin == null)
            goToLogin = new MutableLiveData<>();
        return goToLogin;
    }
    public MutableLiveData<Boolean> getRemoveUserInfo() {
        if(removeUserInfo == null)
            removeUserInfo = new MutableLiveData<>();
        return removeUserInfo;
    }
    public MutableLiveData<Boolean> getImageChanged() {
        if(imageChanged==null)
            imageChanged = new MutableLiveData<>();
        return imageChanged;
    }
    public MutableLiveData<Boolean> getUserInfoUpadated() {
        if(userInfoUpadated==null)
            userInfoUpadated=new MutableLiveData<>();
        return userInfoUpadated;
    }
}
