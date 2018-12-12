package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Repositories.VerificationModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginData> loginData;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<String> toastMessage;
    private VerificationModel verificationModel;

    public void tryLogin(String email, String password){
        if(verificationModel == null)
            verificationModel = new VerificationModel();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            toastMessage.setValue("Please enter a valid email");
            return;
        }
        if(password.length() < 8){
            toastMessage.setValue("Password should be atleast of 8 character");
            return;
        }

        verificationModel.tryLogin(email, password, loginData,  showLoading, toastMessage);
    }

    public MutableLiveData<LoginData> getLoginData() {
        if(loginData == null)
            loginData = new MutableLiveData<>();
        return loginData;
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
}
