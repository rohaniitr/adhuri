package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.UserOpenPostData;
import in.rohansarkar.adhuri.Model.Repositories.UserModel;
import in.rohansarkar.adhuri.Model.Repositories.VerificationModel;

public class UserOpenPostViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserOpenPostData>> postData;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<String> fragmentMessage;
    private UserModel userModel;

    public void getUserOpenPosts(String userId, String token){
        if(userId==null || userId.length()<=0){
            fragmentMessage.setValue("Can not fetch posts for this User");
            return;
        }

        if(userModel == null)
            userModel = new UserModel();
        userModel.getUserOpenPost(userId, token, postData, showLoading, fragmentMessage);
    }

    public MutableLiveData<ArrayList<UserOpenPostData>> getPostData() {
        if(postData == null)
            postData = new MutableLiveData<>();
        return postData;
    }
    public MutableLiveData<Boolean> getShowLoading() {
        if(showLoading == null)
            showLoading = new MutableLiveData<>();
        return showLoading;
    }
    public MutableLiveData<String> getFragmentMessage() {
        if(fragmentMessage == null)
            fragmentMessage = new MutableLiveData<>();
        return fragmentMessage;
    }
}
