package in.rohansarkar.adhuri.View.Fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.UserData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.View.Adapter.ProfilePostAdapter;
import in.rohansarkar.adhuri.ViewModel.ProfileViewModel;

import static in.rohansarkar.adhuri.Util.Util.USER_IMAGE_NAME;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private ProfilePostAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NavController navController;
    private FloatingActionButton fabAddPost;
    private ProgressDialog progressDialog;
    private ProfileViewModel viewModel;
    private LoginData adminData;
    private UserData userData;
    private String userId;
    private ImageView ivImage, ivSettings;
    private TextView tvName, tvBio, tvTags, tvOpenPostCount, tvClosePostCount;
    private OpenPostFeedFragment openPostFeedFragment = new OpenPostFeedFragment();
    private ClosePostFeedFragment closePostFeedFragment = new ClosePostFeedFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        getUserData();
        fillUserData();
        setFragments();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddPost:
                navController.navigate(R.id.action_homeFragment_to_addPostFragment);
                break;
            case R.id.ivSettings:
                navController.navigate(R.id.action_homeFragment_to_userInfoFragment);
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
        navController = Navigation.findNavController(view);

        tabLayout = view.findViewById(R.id.tlPost);
        viewPager = view.findViewById(R.id.vpPost);
        ivImage = view.findViewById(R.id.ivCircularImage);
        tvName = view.findViewById(R.id.tvName);
        tvBio = view.findViewById(R.id.tvBio);
        tvTags = view.findViewById(R.id.tvTags);
        tvOpenPostCount = view.findViewById(R.id.tvOpenPostCount);
        tvClosePostCount = view.findViewById(R.id.tvClosePostCount);
        ivSettings = view.findViewById(R.id.ivSettings);
        fabAddPost = view.findViewById(R.id.fabAddPost);

        fabAddPost.setOnClickListener(this);
        ivSettings.setOnClickListener(this);
    }
    private void observeViewModel(){
        viewModel.getUserData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(@Nullable UserData data) {
                if(userData==null){
                    showToast("Unable to find User");
                    navController.popBackStack();
                    return;
                }

                userData = data;
                fillUserData();
            }
        });
        viewModel.getImageDownloaded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean imageChanged) {
                updateImage();
            }
        });
        viewModel.getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean showLoading) {
                showLoading(showLoading);
            }
        });
        viewModel.getLoadingMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                setLoadingTitle(message);
            }
        });
        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                showToast(message);
            }
        });
    }
    private void getUserData(){
        //Get user info based on userId received
        if(this.getArguments() == null){
            showToast("Unable to get the user data");
            navController.popBackStack();
            return;
        }

        userId = this.getArguments().getString(getResources().getString(R.string.USER_ID));
        //Get Admin's data & check if it's admin's profile
        adminData = PrefUtil.getUserInfo(getActivity());
        if(userId == null || adminData.get_id().equals(userId)){
            //Admin's Profile
            userData = adminData;
            ivSettings.setVisibility(View.VISIBLE);
        }
        else{
            //Get user's data
            viewModel.getUserInfo(userId, adminData.getToken());
            ivSettings.setVisibility(View.GONE);
        }
    }
    private void fillUserData() {
        if(userData ==null)
            return;
        if(userData.getName()!=null)
            tvName.setText(userData.getName());
        if(userData.getBio()!=null)
            tvBio.setText(userData.getBio());
        if(userData.getTags()!=null && userData.getTags().size()>0){
            String s = userData.getTags().get(0);
            for(int i = 1; i< userData.getTags().size(); i++)
                s+= (" " + getActivity().getString(R.string.bullet) + " " + userData.getTags().get(i));
            tvTags.setText(s);
        }

        updateImage();
    }
    private void setFragments(){
        //DO not need to send userID here as these fragments are part of same Activity?
//        Bundle bundle = new Bundle();
//        bundle.putString(this.getString(R.string.USER_ID),  userId);

        if(openPostFeedFragment==null)
            openPostFeedFragment = new OpenPostFeedFragment();
        if(closePostFeedFragment==null)
            closePostFeedFragment = new ClosePostFeedFragment();

        adapter = new ProfilePostAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(openPostFeedFragment, "OPEN");
        adapter.addFragment(closePostFeedFragment, "CLOSE");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    //Updates image from USER_IMAGE_NAME
    private void updateImage() {
        if(adminData.getImage()==null || adminData.getImage().length()<=0)
            return;

        File file = new File(getActivity().getFilesDir(), USER_IMAGE_NAME);
        if(file.exists()) {
            ivImage.setImageURI(null);
            ivImage.setImageURI(Uri.parse(file.getAbsolutePath()));
            return;
        }

        //Else download image
        viewModel.downloadImage(getActivity(), adminData.getImage(), adminData.getToken());
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
    //Shows & Hides ProgressDialog
    private void showLoading(Boolean showLoading){
        if(!showLoading){
            if(progressDialog!=null)
                progressDialog.dismiss();
            return;
        }
        //Else showLoading==true
        if(progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }
    //Sets the title of ProgressDialog
    private void setLoadingTitle(String title){
        if(progressDialog==null)
            progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(title);
    }
}