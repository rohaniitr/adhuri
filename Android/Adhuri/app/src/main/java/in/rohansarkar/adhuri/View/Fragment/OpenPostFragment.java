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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.OpenPost;
import in.rohansarkar.adhuri.Model.Data.UserOpenPostData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Activity.VerificationActivity;
import in.rohansarkar.adhuri.View.Adapter.ClosePostAdapter;
import in.rohansarkar.adhuri.View.Adapter.OpenPostAdapter;
import in.rohansarkar.adhuri.View.Adapter.UserOpenPostAdapter;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;
import in.rohansarkar.adhuri.ViewModel.UserInfoViewModel;
import in.rohansarkar.adhuri.ViewModel.UserOpenPostViewModel;

import static in.rohansarkar.adhuri.Util.Util.USER_IMAGE_NAME;

public class OpenPostFragment extends Fragment {
    private RecyclerView rvPostView;
    private TextView tvFragmentMessage;
    private ProgressBar pbLoading;
    private UserOpenPostAdapter postAdapter;
    private ArrayList<UserOpenPostData> postData;
    private HomeInterface homeInterface;
    private UserOpenPostViewModel viewModel;
    private NavController navController;
    private LoginData userInfo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeInterface = (HomeInterface) context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        setRecyclerView();
        getData();
    }

    //Get data from Server
    private void getData() {
        viewModel.getUserOpenPosts(userInfo.get_id(), userInfo.getToken());
    }
    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(UserOpenPostViewModel.class);
//        navController = Navigation.findNavController(view);

        rvPostView = view.findViewById(R.id.recycler_view);
        tvFragmentMessage = view.findViewById(R.id.tvFragmentMessage);
        pbLoading = view.findViewById(R.id.pbLoading);

        postData = new ArrayList<>();
        userInfo = PrefUtil.getUserInfo(getActivity());
    }
    private void observeViewModel(){
        viewModel.getPostData().observe(this, new Observer<ArrayList<UserOpenPostData>>() {
            @Override
            public void onChanged(@Nullable ArrayList<UserOpenPostData> userOpenPostData) {
                postData.clear();
                postData.addAll(userOpenPostData);
                postAdapter.notifyDataSetChanged();
            }
        });
        viewModel.getFragmentMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                setFragmentMessage(message);
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
    }
    private void setRecyclerView(){
        postAdapter = new UserOpenPostAdapter(getActivity(), homeInterface,  postData);
        rvPostView.setAdapter(postAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvPostView.setLayoutManager(postLayoutManager);
    }
    //Shows & Hides Loading ProgressBar
    private void showLoading(){
        pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideLoading(){
        pbLoading.setVisibility(View.GONE);
    }
    //Sets the title of Fragment Message
    private void setFragmentMessage(String message){
        if(message==null) {
            tvFragmentMessage.setVisibility(View.GONE);
            return;
        }
        tvFragmentMessage.setText(message);
        tvFragmentMessage.setVisibility(View.VISIBLE);
    }
}