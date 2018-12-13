package in.rohansarkar.adhuri.Model.Repositories;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import in.rohansarkar.adhuri.Model.Data.SuccessData;
import in.rohansarkar.adhuri.Model.Retrofit.RepoClient;
import in.rohansarkar.adhuri.Model.Retrofit.RepoInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserModel {
    private String LOG_TAG = this.getClass().getName();

    public void saveUserInfo(final String name, String gender, String bio, final String token, final MutableLiveData<Boolean> showLoading,
                             MutableLiveData<String> progressTitle, final MutableLiveData<String> toastMessage){
        RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
        Call<SuccessData> call = service.saveUserInfo(name, gender, bio, token);
        showLoading.setValue(true);
        progressTitle.setValue("Updating User Info...");

        call.enqueue(new Callback<SuccessData>() {
            @Override
            public void onResponse(Call<SuccessData> call, Response<SuccessData> response) {
                showLoading.setValue(false);
                if((!response.isSuccessful()) || (response.code()!=200) || (!response.body().getSuccess())) {
                    toastMessage.setValue("There is some problem updating User Info");
                    Log.d(LOG_TAG, "saveUserInfo onResponse Failure : " + response.code() + " " + response.isSuccessful());
                    return;
                }

                toastMessage.setValue("User Info updated successfully");
                Log.d(LOG_TAG, "saveUserInfo onResponse Success");
            }

            @Override
            public void onFailure(Call<SuccessData> call, Throwable t) {
                showLoading.setValue(false);
                toastMessage.setValue("Unable to update User Info");
                Log.d(LOG_TAG, "saveUserInfo onFailure");
            }
        });
    }

    public void saveImage(String imageUri, String token, final MutableLiveData<Boolean> showLoading,
                          MutableLiveData<String> progressTitle, final MutableLiveData<String> toastMessage){
        try {
            File file = new File(imageUri);
            Bitmap mBitmap = BitmapFactory.decodeFile(imageUri);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload");

            RepoInterface service = RepoClient.getRetrofitInstance().create(RepoInterface.class);
            Call<ResponseBody> req = service.saveImage(body, name, token);
            showLoading.setValue(true);
            progressTitle.setValue("Uploading Image...");

            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    showLoading.setValue(false);
                    if (!response.isSuccessful() || response.code()!=200) {
                        toastMessage.setValue("Unable to update");
                        return;
                    }
                    toastMessage.setValue("Uploaded Successfully!\n" + response.raw().body().toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showLoading.setValue(false);
                    toastMessage.setValue("Unable to update Picture");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toastMessage.setValue("Unable to update Picture");
        }
    }

    public void deleteAccount(MutableLiveData<Boolean> showLoading, MutableLiveData<String> progressTitle,
                              MutableLiveData<String> toastMessage) {
    }
}
