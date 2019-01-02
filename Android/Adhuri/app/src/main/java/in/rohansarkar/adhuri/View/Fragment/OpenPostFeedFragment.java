package in.rohansarkar.adhuri.View.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.OpenPostData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.View.Adapter.UserOpenPostFeedAdapter;
import in.rohansarkar.adhuri.ViewModel.UserOpenPostFeedViewModel;

public class OpenPostFeedFragment extends Fragment {
    private RecyclerView rvPostView;
    private TextView tvFragmentMessage;
    private ProgressBar pbLoading;
    private UserOpenPostFeedAdapter postAdapter;
    private ArrayList<OpenPostData> postData;
    private UserOpenPostFeedViewModel viewModel;
    private NavController navController;
    private LoginData adminInfo;
    private String userId;

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
        getPostData();
    }

    //Get data from Server
    private void getPostData() {
        //Get user info based on userId received
        if(this.getArguments() == null){
            viewModel.getFeedOpenPosts(adminInfo.getToken());
            return;
        }

        userId = this.getArguments().getString(getResources().getString(R.string.USER_ID));
        if(userId == null){
            showToast("Unable to get the user data");
            navController.popBackStack();
            return;
        }

        viewModel.getUserOpenPosts(adminInfo.get_id(), adminInfo.getToken());
    }
    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(UserOpenPostFeedViewModel.class);
        navController = Navigation.findNavController(view);

        rvPostView = view.findViewById(R.id.recycler_view);
        tvFragmentMessage = view.findViewById(R.id.tvFragmentMessage);
        pbLoading = view.findViewById(R.id.pbLoading);

        postData = new ArrayList<>();
        adminInfo = PrefUtil.getUserInfo(getActivity());
    }
    private void observeViewModel(){
        viewModel.getPostData().observe(this, new Observer<ArrayList<OpenPostData>>() {
            @Override
            public void onChanged(@Nullable ArrayList<OpenPostData> openPostData) {
                postDataChanged(openPostData);
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
        postAdapter = new UserOpenPostFeedAdapter(getActivity(), navController,  postData);
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
    private void postDataChanged(ArrayList<OpenPostData> openPostData) {
        if(openPostData ==null){
            rvPostView.setVisibility(View.GONE);
            return;
        }
        rvPostView.setVisibility(View.VISIBLE);
        postData.clear();
        postData.addAll(openPostData);
        postAdapter.notifyDataSetChanged();
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}