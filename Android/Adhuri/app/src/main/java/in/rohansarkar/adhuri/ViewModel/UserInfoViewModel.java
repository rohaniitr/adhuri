package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import in.rohansarkar.adhuri.Model.Repositories.UserModel;

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<Boolean> goToLogin;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<String> loadingTitle;
    private MutableLiveData<String> toastMessage;
    private MutableLiveData<Boolean> removeUserInfo;
    private UserModel userModel;

    public void saveUserInfo(String name, String gender, String bio, String token){
        if(name.length() < 0){
            toastMessage.setValue("Please enter a valid name");
            return;
        }

        if(userModel == null)
            userModel = new UserModel();
        userModel.saveUserInfo(name, gender, bio, token, showLoading, loadingTitle, toastMessage);
    }
    public void saveImage(String imageUri, String token){
        if(imageUri == null)
            toastMessage.setValue("Can't get the image. Please select another image.");

        if(userModel == null)
            userModel = new UserModel();
        userModel.saveImage(imageUri, token, showLoading, loadingTitle, toastMessage);
    }
    public void logout(){
        removeUserInfo.setValue(true);
        goToLogin.setValue(true);
    }
    public void deleteAccount(){
        if(userModel == null)
            userModel = new UserModel();
        userModel.deleteAccount(showLoading, loadingTitle, toastMessage);
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
}
