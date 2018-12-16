package in.rohansarkar.adhuri.View.Fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Activity.VerificationActivity;
import in.rohansarkar.adhuri.View.Adapter.ProfilePostAdapter;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;
import in.rohansarkar.adhuri.ViewModel.ProfileViewModel;
import in.rohansarkar.adhuri.ViewModel.UserInfoViewModel;

import static in.rohansarkar.adhuri.Util.Util.USER_IMAGE_NAME;

public class ProfileFragment extends Fragment {
    private ProfilePostAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    //Use NavController instead
    private HomeInterface homeInterface;
    private FloatingActionButton fabAddPost;
    private ProgressDialog progressDialog;
    private ProfileViewModel viewModel;
    private LoginData userInfo;
    private ImageView ivImage, ivSettings;
    private TextView tvName, tvBio, tvTags, tvOpenPostCount, tvClosePostCount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeInterface = (HomeInterface) context;
    }
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

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
//        navController = Navigation.findNavController(view);

        fabAddPost = (FloatingActionButton) view.findViewById(R.id.fabAddPost);
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.showAddPostFragment();
            }
        });

        tabLayout = view.findViewById(R.id.tlPost);
        viewPager = view.findViewById(R.id.vpPost);
        ivImage = view.findViewById(R.id.ivCircularImage);
        tvName = view.findViewById(R.id.tvName);
        tvBio = view.findViewById(R.id.tvBio);
        tvTags = view.findViewById(R.id.tvTags);
        tvOpenPostCount = view.findViewById(R.id.tvOpenPostCount);
        tvClosePostCount = view.findViewById(R.id.tvClosePostCount);

        adapter = new ProfilePostAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OpenPostFragment(), "OPEN");
        adapter.addFragment(new ClosePostFragment(), "CLOSE");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ivSettings = (ImageView) view.findViewById(R.id.ivSettings);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.showUserinfoFragment();
            }
        });

        userInfo = PrefUtil.getUserInfo(getActivity());
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