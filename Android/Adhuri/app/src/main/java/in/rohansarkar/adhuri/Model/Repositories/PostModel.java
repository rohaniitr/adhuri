package in.rohansarkar.adhuri.Model.Repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.Model.Retrofit.RepoClient;
import in.rohansarkar.adhuri.Model.Retrofit.RepoInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostModel {
    private String LOG_TAG = this.getClass().getName();

    public void getSuggestions(final String postId, final String token,
                               final MutableLiveData<ArrayList<SuggestionData>> suggestionList,
                               final MutableLiveData<Boolean> showLoading, final MutableLiveData<String> fragmentMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<ArrayList<SuggestionData>> call = service.getSuggestions(postId, token);
        showLoading.setValue(true);
        fragmentMessage.setValue("Fetching Suggestions...");

        call.enqueue(new Callback<ArrayList<SuggestionData>>() {
            @Override
            public void onResponse(Call<ArrayList<SuggestionData>> call, Response<ArrayList<SuggestionData>> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200)) {
                    fragmentMessage.setValue("There is some problem fetching suggestions for this post");
                    Log.d(LOG_TAG, "getSuggestions onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                Log.d(LOG_TAG, "getSuggestions onResponse Success");
                if(response.body()==null || response.body().size()<=0) {
                    fragmentMessage.setValue("No suggestions for this post");
                    suggestionList.setValue(null);
                }
                else {
                    fragmentMessage.setValue(null);
                    suggestionList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SuggestionData>> call, Throwable t) {
                showLoading.setValue(false);
                fragmentMessage.setValue("Unable to fetch suggestions for this post");
                Log.d(LOG_TAG, "getSuggestions onFailure");
            }
        });
    }
}
