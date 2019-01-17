package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.Model.Repositories.PostModel;
import in.rohansarkar.adhuri.Model.Repositories.UserModel;

public class ViewOpenPostViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SuggestionData>> suggestionList;
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<String> fragmentMessage;
    private PostModel postModel;

    public void getSuggestions(String userId, String token){
        if(userId==null || userId.length()<=0){
            fragmentMessage.setValue("Can not fetch posts for this User");
            return;
        }

        if(postModel == null)
            postModel = new PostModel();
        postModel.getSuggestions(userId, token, suggestionList, showLoading, fragmentMessage);
    }

    public MutableLiveData<ArrayList<SuggestionData>> getSuggestionList() {
        if(suggestionList == null)
            suggestionList = new MutableLiveData<>();
        return suggestionList;
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
