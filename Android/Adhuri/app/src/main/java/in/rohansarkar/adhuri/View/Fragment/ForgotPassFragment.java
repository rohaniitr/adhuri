package in.rohansarkar.adhuri.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Interface.SwitchInterface;

public class ForgotPassFragment extends Fragment implements View.OnClickListener{
    private Button bLogin,  bSubmit;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forgot_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        bLogin = (Button) view.findViewById(R.id.bLogin);
        bSubmit = (Button) view.findViewById(R.id.bSubmit);
        bLogin.setOnClickListener(this);
        bSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bSubmit:
                navController.popBackStack();
                break;
            case R.id.bLogin:
                navController.popBackStack();
                break;
        }
    }
}