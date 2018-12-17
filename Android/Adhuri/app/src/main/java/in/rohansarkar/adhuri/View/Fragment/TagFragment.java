package in.rohansarkar.adhuri.View.Fragment;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.Tag;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Adapter.TagAdapter;
import in.rohansarkar.adhuri.ViewModel.TagViewModel;

public class TagFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rvTagView;
    private TagAdapter tagAdapter;
    private ArrayList<Tag> tagList;
    private ImageView ivBack;
    private Button bSubmit;
    private ProgressDialog progressDialog;
    private LoginData userData;
    private TagViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        observeViewModel();
        setRecyclerView();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSubmit:
                viewModel.updateTags(tagList, userData.getToken());
                break;
            case R.id.ivBack:
                break;
        }
    }

    private void initialise(View view) {
        viewModel = ViewModelProviders.of(getActivity()).get(TagViewModel.class);
        //navController = Navigation.findNavController(view);

        ivBack = view.findViewById(R.id.ivBack);
        bSubmit = view.findViewById(R.id.bSubmit);
        rvTagView = view.findViewById(R.id.rvTag);

        ivBack.setOnClickListener(this);
        bSubmit.setOnClickListener(this);

        userData = PrefUtil.getUserInfo(getActivity());
        tagList = new ArrayList<>();
        for(int i=0; i<Util.genreList.size(); i++) {
            Tag data = new Tag(Util.genreList.get(i), userData.getTags().contains(Util.genreList.get(i)));
            tagList.add(data);
        }
    }
    private void observeViewModel(){
        viewModel.getTagsUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean tagsUpdated) {
                updateTags();
            }
        });
        viewModel.getShowLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean showLoading) {
                if(showLoading) showLoading();
                else hideLoading();
            }
        });
        viewModel.getLoadingTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String loadingTitle) {
                if(loadingTitle!= null)
                    setLoadingTitle(loadingTitle);
            }
        });
        viewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String toastMessage) {
                showToast(toastMessage);
            }
        });
    }
    private void setRecyclerView(){
        tagAdapter = new TagAdapter(getActivity(), tagList);
        rvTagView.setAdapter(tagAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvTagView.setLayoutManager(postLayoutManager);
    }
    //Update Tags
    private void updateTags(){
        ArrayList<String> newTagList = new ArrayList<>();
        for(int i=0; i<tagList.size(); i++){
            if(tagList.get(i).isSelected())
                newTagList.add(tagList.get(i).getTag());
        }

        userData.setTags(newTagList);
        PrefUtil.setUserInfo(getActivity(), userData);
    }
    //Shows & Hides ProgressDialog
    private void showLoading(){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
        }
        progressDialog.show();
    }
    private void hideLoading(){
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
    //Sets the title of ProgressDialog
    private void setLoadingTitle(String title){
        if(progressDialog==null)
            progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(title);
    }
    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}