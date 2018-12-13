package in.rohansarkar.adhuri.Model.Repositories;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.SuccessData;
import in.rohansarkar.adhuri.Model.Retrofit.RepoClient;
import in.rohansarkar.adhuri.Model.Retrofit.RepoInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationModel {
    public String LOG_TAG = this.getClass().getName();

    public void tryLogin(String email, String password, final MutableLiveData<LoginData> loginData,
                         final MutableLiveData<Boolean> showLoading, final MutableLiveData<String> toastMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<LoginData> call = service.loginUser(email,password);
        showLoading.setValue(true);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                showLoading.setValue(false);
                if(!response.isSuccessful() || response.code()!=200) {
                    toastMessage.setValue("Invalid Login Credentials");
                    Log.d(LOG_TAG, "onResponse Failure");
                    return;
                }

                loginData.setValue(response.body());
                Log.d(LOG_TAG, "onResponse Success : " + response.body().getName());
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                showLoading.setValue(false);
                loginData.setValue(null);
                toastMessage.setValue("Unable to Login...");
                Log.d(LOG_TAG, "onFailure");
            }
        });
    }

    public void registerUser(final String name, final String email, String password, final MutableLiveData<Boolean> registerSuccess,
                             final MutableLiveData<Boolean> showLoading, final MutableLiveData<String> toastMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<SuccessData> call = service.registerUser(name, email, password);
        showLoading.setValue(true);

        call.enqueue(new Callback<SuccessData>() {
            @Override
            public void onResponse(Call<SuccessData> call, Response<SuccessData> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200) || (!response.body().getSuccess())) {
                    toastMessage.setValue("There is some problem registering the User");
                    Log.d(LOG_TAG, "registerUser onResponse Failure");
                    return;
                }

                registerSuccess.setValue(true);
                toastMessage.setValue("User Registered successfully!\n Please login with same credentials.");
                Log.d(LOG_TAG, "registerUser onResponse Success : " + name);
            }

            @Override
            public void onFailure(Call<SuccessData> call, Throwable t) {
                showLoading.setValue(false);
                toastMessage.setValue("Unable to register user");
                Log.d(LOG_TAG, "registerUser onFailure");
            }
        });
    }
}
