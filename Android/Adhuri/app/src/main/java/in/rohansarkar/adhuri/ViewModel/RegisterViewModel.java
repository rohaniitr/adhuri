package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Repositories.VerificationModel;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<Boolean> registerSuccess;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<String> toastMessage;
    private VerificationModel verificationModel;

    public void registerUser(String name, String email, String password){
        if(verificationModel == null)
            verificationModel = new VerificationModel();

        if(name.length()<=0){
            toastMessage.setValue("Please enter a valid name");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            toastMessage.setValue("Please enter a valid email");
            return;
        }
        if(password.length() < 8){
            toastMessage.setValue("Password should be atleast of 8 character");
            return;
        }

        verificationModel.registerUser(name, email, password, registerSuccess, showLoading, toastMessage);
    }

    public MutableLiveData<Boolean> getRegisterSuccess() {
        if(registerSuccess == null)
            registerSuccess = new MutableLiveData<>();
        return registerSuccess;
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
