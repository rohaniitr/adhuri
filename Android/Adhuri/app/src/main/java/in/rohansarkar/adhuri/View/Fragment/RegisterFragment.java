package in.rohansarkar.adhuri.View.Fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.ViewModel.RegisterViewModel;

public class RegisterFragment extends Fragment implements View.OnClickListener{
    boolean passwordVisible = false;
    private EditText etEmail, etPassword, etName;
    private ImageView ivPassVisibility;
    private Button bLogin, bRegister;
    private NavController navController;
    private RegisterViewModel viewModel;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
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
            case R.id.bLogin:
                navController.popBackStack();
                break;
            case R.id.bRegister:
                viewModel.registerUser(etName.getText().toString().trim(), etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim());
                break;
            case R.id.ivPasswordVisible:
                togglePasswordVisibility();
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(RegisterViewModel.class);
        navController = Navigation.findNavController(view);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        bLogin = view.findViewById(R.id.bLogin);
        bRegister = view.findViewById(R.id.bRegister);
        ivPassVisibility = view.findViewById(R.id.ivPasswordVisible);

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);
        ivPassVisibility.setOnClickListener(this);
    }
    private void observeViewModel(){
        viewModel.getRegisterSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean registerSuccess) {
                if(registerSuccess)
                    navController.popBackStack();
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
}