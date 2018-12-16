package in.rohansarkar.adhuri.View.Fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Activity.VerificationActivity;
import in.rohansarkar.adhuri.ViewModel.UserInfoViewModel;

import static android.app.Activity.RESULT_OK;
import static in.rohansarkar.adhuri.Util.Util.REQUEST_GET_IMAGE;
import static in.rohansarkar.adhuri.Util.Util.USER_IMAGE_NAME;

public class UserInfoFragment extends Fragment implements View.OnClickListener{
    private String LOG_TAG = this.getClass().getName();
    private UserInfoViewModel viewModel;
    private NavController navController;
    private Spinner sGender;
    private ImageView ivImage;
    private TextView tvChangeImage;
    private EditText etName, etBio;
    private LoginData userInfo;
    private ProgressDialog progressDialog;
    private Button bSave, bLogout, bDeleteAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        fillData();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode== REQUEST_GET_IMAGE) {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    Bitmap photo = extras2.getParcelable("data");
                    viewModel.uploadImage(getActivity(), photo, userInfo.getToken());
                }
                else
                    showToast("NULL");
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    String path = getPathFromURI(selectedImageUri);
//                    Log.i(LOG_TAG, "Image Path : " + path);
//                    viewModel.uploadImage(path, userInfo.getToken());
//                }
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "File select error");
            showToast("Unable to get the image. Please select another image.");
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tvChangePhoto:
            case R.id.ivCircularImage:
                viewModel.getImage(this);
                break;
            case R.id.bSave:
                viewModel.saveUserInfo(etName.getText().toString().trim(), sGender.getSelectedItem().toString(),
                        etBio.getText().toString().trim(), userInfo.getToken());
                break;
            case R.id.bLogout:
                viewModel.logout();
                break;
            case R.id.bDeleteAccount:
                viewModel.deleteAccount();
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(UserInfoViewModel.class);
//        navController = Navigation.findNavController(view);

        ivImage = view.findViewById(R.id.ivCircularImage);
        tvChangeImage =  view.findViewById(R.id.tvChangePhoto);
        etName = view.findViewById(R.id.etName);
        etBio = view.findViewById(R.id.etBio);
        bSave = view.findViewById(R.id.bSave);
        bLogout = view.findViewById(R.id.bLogout);
        bDeleteAccount = view.findViewById(R.id.bDeleteAccount);

        ivImage.setOnClickListener(this);
        tvChangeImage.setOnClickListener(this);
        bSave.setOnClickListener(this);
        bLogout.setOnClickListener(this);
        bDeleteAccount.setOnClickListener(this);

        setSpinner(view);

        userInfo = PrefUtil.getUserInfo(getActivity());
    }
    private void observeViewModel(){
        viewModel.getUserInfoUpadated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean userInfoUpadated) {
                userInfo.setName(etName.getText().toString().trim());
                userInfo.setBio(etBio.getText().toString().trim());
                userInfo.setGender(sGender.getSelectedItem().toString());
                setUserInfo(userInfo);
            }
        });
        viewModel.getImageChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean imageChanged) {
                updateImage();
            }
        });
        viewModel.getRemoveUserInfo().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean removeUserInfo) {
                if(removeUserInfo)
                    removeUserInfo();
            }
        });
        viewModel.getGoToLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean goToLogin) {
                if(goToLogin)
                    goToLogin();
            }
        });
        viewModel.getLoadingTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String loadingTitle) {
                setLoadingTitle(loadingTitle);
            }
        });
        viewModel.getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean showLoading) {
                if(showLoading)
                    showLoading();
                else
                    hideLoading();
            }
        });
        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String toastMessage) {
                showToast(toastMessage);
            }
        });
    }
    private void setSpinner(View view){
        sGender = view.findViewById(R.id.sGender);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                Util.genderList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGender.setAdapter(dataAdapter);
    }
    private void fillData() {
        if(userInfo ==null)
            return;
        if(userInfo.getName()!=null)
            etName.setText(userInfo.getName());
        if(userInfo.getBio()!=null)
            etBio.setText(userInfo.getBio());
        if(userInfo.getGender()!=null)
            sGender.setSelection(Util.genderList.indexOf(userInfo.getGender()));

        updateImage();
    }
    //Updates image from USER_IMAGE_NAME
    private void updateImage() {
        File file = new File(getActivity().getFilesDir(), USER_IMAGE_NAME);
        if(file.exists()) {
            ivImage.setImageURI(null);
            ivImage.setImageURI(Uri.parse(file.getAbsolutePath()));
        }
    }
    //Shows & Hides ProgressDialog
    private void showLoading(){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }
    private void hideLoading(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
    //Sets the title of ProgressDialog
    private void setLoadingTitle(String title){
        if(progressDialog==null)
            progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(title);
    }
    //Kill and go to login
    private void goToLogin(){
        Intent iLogin = new Intent(getActivity(), VerificationActivity.class);
        startActivity(iLogin);
        getActivity().finish();
    }
    private void setUserInfo(LoginData loginData){
        PrefUtil.setUserInfo(getActivity(), loginData);
    }
    private void removeUserInfo(){
        PrefUtil.setUserInfo(getActivity(), null);
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}