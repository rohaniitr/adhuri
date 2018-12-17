package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.UserClosePostData;
import in.rohansarkar.adhuri.Model.Data.UserOpenPostData;
import in.rohansarkar.adhuri.Model.Repositories.UserModel;

public class UserClosePostFeedViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserClosePostData>> postData;
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
        userModel.getUserClosePost(userId, token, postData, showLoading, fragmentMessage);
    }

    public MutableLiveData<ArrayList<UserClosePostData>> getPostData() {
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
