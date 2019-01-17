package in.rohansarkar.adhuri.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Activity.VerificationActivity;
import in.rohansarkar.adhuri.View.Adapter.ProfilePostAdapter;
import in.rohansarkar.adhuri.View.Utils.CustomAlertAction;
import in.rohansarkar.adhuri.View.Utils.CustomAlertBox;

public class FeedFragment extends Fragment implements View.OnClickListener{
    private ProfilePostAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivBack, ivLogout;
    private NavController navController;
    private FloatingActionButton fabAddPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddPost:
                navController.navigate(R.id.action_homeFragment_to_addPostFragment);
                break;
            case R.id.ivBack:
                navController.popBackStack();
                break;
            case R.id.ivLogout:
                launchLogoutAlert();
                break;
        }
    }

    private void initialise(View view) {
        navController = Navigation.findNavController(view);

        ivBack = view.findViewById(R.id.ivBack);
        ivLogout = view.findViewById(R.id.ivLogout);
        fabAddPost = view.findViewById(R.id.fabAddPost);

        fabAddPost.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivLogout.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tlPost);
        viewPager = view.findViewById(R.id.vpPost);

        adapter = new ProfilePostAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OpenPostFeedFragment(), "OPEN");
        adapter.addFragment(new ClosePostFeedFragment(), "CLOSE");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void launchLogoutAlert(){
        CustomAlertBox alertBox = new CustomAlertBox();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.alert_title_id), R.string.logout);
        bundle.putInt(getString(R.string.alert_icon_id), R.drawable.icon_logout);
        bundle.putInt(getString(R.string.alert_message_id), R.string.logout_alert_content);
        bundle.putSerializable(getString(R.string.alert_action_object), new CustomAlertAction() {
            @Override
            public void onSuccess() {
                logout();
            }
            @Override
            public void onError() {

            }
        });
        alertBox.setArguments(bundle);
        alertBox.show(getActivity().getSupportFragmentManager(), null);
    }
    private void logout(){
        removeUserInfo();
        goToLogin();
    }
    //Kill and go to login
    private void goToLogin(){
        Intent iLogin = new Intent(getActivity(), VerificationActivity.class);
        startActivity(iLogin);
        getActivity().finish();
    }
    private void removeUserInfo(){
        PrefUtil.setUserInfo(getActivity(), null);

        File file = new File(getActivity().getFilesDir().getAbsolutePath() + File.separator + Util.USER_IMAGE_NAME);
        if(file.exists())
            file.delete();
    }
}