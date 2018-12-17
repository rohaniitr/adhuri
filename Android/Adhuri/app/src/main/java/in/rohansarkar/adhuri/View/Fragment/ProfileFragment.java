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
    private LoginData userInfo;
    private ImageView ivImage, ivSettings;
    private TextView tvName, tvBio, tvTags, tvOpenPostCount, tvClosePostCount;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        fillData();
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
        ivSettings = (ImageView) view.findViewById(R.id.ivSettings);
        fabAddPost = view.findViewById(R.id.fabAddPost);

        fabAddPost.setOnClickListener(this);
        ivSettings.setOnClickListener(this);

        adapter = new ProfilePostAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OpenPostFeedFragment(), "OPEN");
        adapter.addFragment(new ClosePostFeedFragment(), "CLOSE");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //Get user info based on userId received
        if (this.getArguments() == null || this.getArguments().getString(getResources().getString(R.string.USER_ID)) == null ||
                this.getArguments().getString(getResources().getString(R.string.USER_ID)) == PrefUtil.getUserInfo(getActivity()).get_id())
            userId = null;
        else
            userId = this.getArguments().getString(getResources().getString(R.string.USER_ID));

        if (userId == null){
            //If user is owner
            userInfo = PrefUtil.getUserInfo(getActivity());
            ivSettings.setVisibility(View.VISIBLE);
        } else {
            //Some other user
            //Fetch User details
            ivSettings.setVisibility(View.GONE);
        }
    }
    private void observeViewModel(){
        viewModel.getImageDownloaded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean imageChanged) {
                updateImage();
            }
        });
        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                showToast(message);
            }
        });
    }
    private void fillData() {
        if(userInfo ==null)
            return;
        if(userInfo.getName()!=null)
            tvName.setText(userInfo.getName());
        if(userInfo.getBio()!=null)
            tvBio.setText(userInfo.getBio());
        if(userInfo.getTags()!=null && userInfo.getTags().size()>0){
            String s = userInfo.getTags().get(0);
            for(int i=1; i<userInfo.getTags().size(); i++)
                s+= (" " + getActivity().getString(R.string.bullet) + " " + userInfo.getTags().get(i));
            tvTags.setText(s);
        }

        updateImage();
    }
    //Updates image from USER_IMAGE_NAME
    private void updateImage() {
        if(userInfo.getImage()==null || userInfo.getImage().length()<=0)
            return;

        File file = new File(getActivity().getFilesDir(), USER_IMAGE_NAME);
        if(file.exists()) {
            ivImage.setImageURI(null);
            ivImage.setImageURI(Uri.parse(file.getAbsolutePath()));
            return;
        }

        //Else download image
        viewModel.downloadImage(getActivity(), userInfo.getImage(), userInfo.getToken());
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}