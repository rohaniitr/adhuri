package in.rohansarkar.adhuri.View.Fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Activity.HomeActivity;
import in.rohansarkar.adhuri.View.Interface.SwitchInterface;
import in.rohansarkar.adhuri.ViewModel.LoginViewModel;

public class LoginFragment extends Fragment implements View.OnClickListener{

    boolean passwordVisible = false;
    private EditText etEmail, etPassword;
    private ImageView ivPassVisibility;
    private Button bLogin, bRegister;
    private TextView bForgotPass;
    private NavController navController;
    private LoginViewModel viewModel;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialise(view);
        observeViewModel();
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivPasswordVisible:
                togglePasswordVisibility();
                break;
            case R.id.tvForgotPassword:
                navController.navigate(R.id.action_loginFragment_to_forgotPassFragment);
                break;
            case R.id.bLogin:
                viewModel.tryLogin(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
//                File file = new File(getActivity().getFilesDir() + File.separator + Util.USER_IMAGE_NAME);
//                if(file.exists()) {
//                    file.delete();
//                    showToast("Deleted");
//                }
//                goToHome();
                break;
            case R.id.bRegister:
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        navController = Navigation.findNavController(view);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        bLogin = view.findViewById(R.id.bLogin);
        bRegister = view.findViewById(R.id.bRegister);
        bForgotPass = view.findViewById(R.id.tvForgotPassword);
        ivPassVisibility = view.findViewById(R.id.ivPasswordVisible);

        ivPassVisibility.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        bForgotPass.setOnClickListener(this);

        etEmail.setText("d@e.com");
        etPassword.setText("somepassword");
    }
    private void observeViewModel(){
        viewModel.getLoginData().observe(this, new Observer<LoginData>() {
            @Override
            public void onChanged(@Nullable LoginData loginData) {
                if(loginData== null)
                    return;
                setUserInfo(loginData);
                goToHome();
            }
        });
        viewModel.getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean showLoading) {
                if(showLoading==null)
                    return;
                if(showLoading==true)
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
    //Shows & Hides ProgressDialog
    private void showLoading(){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Logging you in...");
        }
        progressDialog.show();
    }
    private void hideLoading(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
    //Toggles password visibility
    private void togglePasswordVisibility(){
        passwordVisible = !passwordVisible;
        if(passwordVisible) {
            etPassword.setTransformationMethod(null);
            ivPassVisibility.setBackground(getResources().getDrawable(R.drawable.icon_visibility_off));
        }
        else {
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            ivPassVisibility.setBackground(getResources().getDrawable(R.drawable.icon_visibility_on));
        }
        etPassword.setSelection(etPassword.length());   //To place cursor at last
    }
    private void goToHome(){
        Intent iHome = new Intent(getActivity(), HomeActivity.class);
        startActivity(iHome);
        getActivity().finish();
    }
    //Saves user info in SharedPreferences
    private void setUserInfo(LoginData loginData){
        PrefUtil.setUserInfo(getActivity(), loginData);
    }
}