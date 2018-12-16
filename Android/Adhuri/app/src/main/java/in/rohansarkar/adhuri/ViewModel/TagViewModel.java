package in.rohansarkar.adhuri.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Tag;
import in.rohansarkar.adhuri.Model.Repositories.UserModel;

import static in.rohansarkar.adhuri.Util.Util.REQUEST_GET_IMAGE;

public class TagViewModel extends ViewModel {
    private MutableLiveData<Boolean> showLoading;
    private MutableLiveData<Boolean> tagsUpdated;
    private MutableLiveData<String> loadingTitle;
    private MutableLiveData<String> toastMessage;
    private UserModel userModel;

    //Updates Tag preference for user in Server
    public void updateTags(ArrayList<Tag> tagDataList, String token){
        ArrayList<String> tagList = new ArrayList<>();
        for(int i=0; i<tagDataList.size(); i++){
            if(tagDataList.get(i).isSelected())
                tagList.add(tagDataList.get(i).getTag());
        }
        if(tagList.size()<=0){
            toastMessage.setValue("Please select at least 1 Tag");
            return;
        }

        if(userModel == null)
            userModel = new UserModel();
        userModel.updateTags(tagList, token, showLoading, loadingTitle, toastMessage, tagsUpdated);
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
    public MutableLiveData<Boolean> getTagsUpdated() {
        if(tagsUpdated==null)
            tagsUpdated=new MutableLiveData<>();
        return tagsUpdated;
    }
}
