package in.rohansarkar.adhuri.View.Fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.Model.Data.UserOpenPostData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Adapter.OpenPostAdapter;
import in.rohansarkar.adhuri.ViewModel.OpenPostViewModel;

public class OpenPostFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvSuggestions;
    private TextView tvFragmentMessage;
    private ProgressBar pbLoading;
    private UserOpenPostData postData;
    private OpenPostAdapter suggestionAdapter;
    private ArrayList<SuggestionData> suggestionList;
    private OpenPostViewModel viewModel;
    private NavController navController;
    private LoginData userInfo;
    private ImageView ivProfileImage;
    private TextView tvName, tvTime, tvContent;
    private EditText etSuggestion;
    private FloatingActionButton fabAddSuggestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_open_post, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        setRecyclerView();
        getData();
        setPostInfo();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddSuggestion:
                showToast("Adding " + etSuggestion.getText().toString());
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(OpenPostViewModel.class);
        navController = Navigation.findNavController(view);

        rvSuggestions = view.findViewById(R.id.recycler_view);
        tvFragmentMessage = view.findViewById(R.id.tvFragmentMessage);
        pbLoading = view.findViewById(R.id.pbLoading);
        ivProfileImage = view.findViewById(R.id.ivCircularImage);
        tvName = view.findViewById(R.id.tvName);
        tvTime = view.findViewById(R.id.tvTime);
        tvContent = view.findViewById(R.id.tvContent);
        etSuggestion = view.findViewById(R.id.etSuggestion);
        fabAddSuggestion = view.findViewById(R.id.fabAddSuggestion);

        fabAddSuggestion.setOnClickListener(this);

        suggestionList = new ArrayList<>();
    }
    private void observeViewModel(){
        viewModel.getSuggestionList().observe(this, new Observer<ArrayList<SuggestionData>>() {
            @Override
            public void onChanged(@Nullable ArrayList<SuggestionData> suggestionList) {
                suggestionListChanged(suggestionList);
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
        suggestionAdapter = new OpenPostAdapter(getActivity(), navController, suggestionList);
        rvSuggestions.setAdapter(suggestionAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvSuggestions.setLayoutManager(postLayoutManager);
    }
    private void setPostInfo(){
        if(postData==null){
            showToast("Unable to open this post");
            navController.popBackStack();
            return;
        }

        tvName.setText(postData.getName());
        tvTime.setText(postData.getTime());
        tvContent.setText(postData.getContent());

        if(postData.getImage() != null)
            Picasso.get().load(Util.baseUrl + '/' + postData.getImage()).into(ivProfileImage);
    }
    //Get data from Server
    private void getData() {
        Bundle bundle = this.getArguments();
        if(bundle==null) {
            showToast("Ünable to open this post");
            navController.popBackStack();
            return;
        }

        postData = (UserOpenPostData) bundle.getSerializable(this.getResources().getString(R.string.PASS_POST));
        if(postData==null) {
            showToast("Ünable to open this post");
            navController.popBackStack();
            return;
        }

        userInfo = PrefUtil.getUserInfo(getActivity());
        viewModel.getSuggestions(postData.get_id(), userInfo.getToken());
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
    private void suggestionListChanged(ArrayList<SuggestionData> userOpenPostData) {
        if(userOpenPostData==null){
            rvSuggestions.setVisibility(View.GONE);
            return;
        }
        rvSuggestions.setVisibility(View.VISIBLE);
        suggestionList.clear();
        suggestionList.addAll(userOpenPostData);
        suggestionAdapter.notifyDataSetChanged();
    }
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}