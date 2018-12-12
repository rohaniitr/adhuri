package in.rohansarkar.adhuri.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.OpenPost;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.OpenPostAdapter;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class OpenPostFragment extends Fragment {
    private RecyclerView rvPostView;
    private OpenPostAdapter postAdapter;
    private ArrayList<OpenPost> postData;
    private HomeInterface homeInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeInterface = (HomeInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        fillData();

        rvPostView = (RecyclerView) view.findViewById(R.id.recycler_view);
        postAdapter = new OpenPostAdapter(getActivity(), homeInterface, postData);
        rvPostView.setAdapter(postAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvPostView.setLayoutManager(postLayoutManager);

        return view;
    }

    private void fillData(){
        postData = new ArrayList<>();

        OpenPost data1 = new OpenPost(getResources().getString(R.string.sample_name1),
                getResources().getDrawable(R.drawable.profilepicture5),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_1),
                7);
        postData.add(data1);

        OpenPost data2 = new OpenPost(getResources().getString(R.string.sample_name2),
                getResources().getDrawable(R.drawable.profilepicture2),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_2),
                7);
        postData.add(data2);

        OpenPost data3 = new OpenPost(getResources().getString(R.string.sample_name3),
                getResources().getDrawable(R.drawable.profilepicture3),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_3),
                7);
        postData.add(data3);

        OpenPost data4 = new OpenPost(getResources().getString(R.string.sample_name4),
                getResources().getDrawable(R.drawable.profilepicture4),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_1),
                7);
        postData.add(data4);

        OpenPost data5 = new OpenPost(getResources().getString(R.string.sample_name5),
                getResources().getDrawable(R.drawable.profilepicture5),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_2),
                7);
        postData.add(data5);
    }
}