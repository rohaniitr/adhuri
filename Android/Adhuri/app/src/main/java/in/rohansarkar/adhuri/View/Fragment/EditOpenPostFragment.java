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
import android.widget.Button;
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
import in.rohansarkar.adhuri.Model.Data.PostData;
import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Adapter.SuggestionAdapter;
import in.rohansarkar.adhuri.View.Utils.CustomAlertAction;
import in.rohansarkar.adhuri.View.Utils.CustomAlertBox;
import in.rohansarkar.adhuri.ViewModel.EditOpenPostViewModel;

public class EditOpenPostFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvSuggestions;
    private TextView tvFragmentMessage;
    private ProgressBar pbLoading;
    private PostData postData;
    private SuggestionAdapter suggestionAdapter;
    private ArrayList<SuggestionData> suggestionList;
    private EditOpenPostViewModel viewModel;
    private NavController navController;
    private LoginData adminInfo;
    private TextView tvName, tvTime;
    private ImageView ivProfileImage;
    private ImageView ivDeletePost;
    private ImageView ivBack;
    private EditText etContent;
    private Button bSavePost, bClosePost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_open_post, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        getData();
        setRecyclerView();
        setPostInfo();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSavePost:
                viewModel.savePost();
                break;
            case R.id.bClosePost:
                viewModel.closePost();
                break;
            case R.id.ivDeletePost:
                launchDeletePostAlert();
                break;
            case R.id.ivBack:
                navController.popBackStack();
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(EditOpenPostViewModel.class);
        navController = Navigation.findNavController(view);

        rvSuggestions = view.findViewById(R.id.recycler_view);
        tvFragmentMessage = view.findViewById(R.id.tvFragmentMessage);
        pbLoading = view.findViewById(R.id.pbLoading);
        ivProfileImage = view.findViewById(R.id.ivCircularImage);
        tvName = view.findViewById(R.id.tvName);
        tvTime = view.findViewById(R.id.tvTime);
        etContent = view.findViewById(R.id.etContent);
        ivDeletePost = view.findViewById(R.id.ivDeletePost);
        ivBack = view.findViewById(R.id.ivBack);
        bSavePost= view.findViewById(R.id.bSavePost);
        bClosePost= view.findViewById(R.id.bClosePost);

        ivBack.setOnClickListener(this);
        ivDeletePost.setOnClickListener(this);
        bSavePost.setOnClickListener(this);
        bClosePost.setOnClickListener(this);

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
        suggestionAdapter = new SuggestionAdapter(getActivity(), navController, suggestionList, etContent, postData.getUserId());
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
        etContent.setText(postData.getContent());

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

        postData = (PostData) bundle.getSerializable(this.getResources().getString(R.string.PASS_POST));
        if(postData==null) {
            showToast("Ünable to open this post");
            navController.popBackStack();
            return;
        }

        adminInfo = PrefUtil.getUserInfo(getActivity());
        viewModel.getSuggestions(postData.get_id(), adminInfo.getToken());
    }
    private void launchDeletePostAlert(){
        CustomAlertBox alertBox = new CustomAlertBox();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.alert_title_id), R.string.delete_post);
        bundle.putInt(getString(R.string.alert_icon_id), R.drawable.icon_delete);
        bundle.putInt(getString(R.string.alert_message_id), R.string.delete_post_alert_content);
        bundle.putSerializable(getString(R.string.alert_action_object), new CustomAlertAction() {
            @Override
            public void onSuccess() {
                viewModel.deletePost();
            }
            @Override
            public void onError() {

            }
        });
        alertBox.setArguments(bundle);
        alertBox.show(getActivity().getSupportFragmentManager(), null);
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