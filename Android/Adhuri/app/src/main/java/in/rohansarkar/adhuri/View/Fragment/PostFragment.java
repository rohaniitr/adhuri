package in.rohansarkar.adhuri.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Post;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.PostAdapter;

public class PostFragment extends Fragment {
    private RecyclerView rvSuggestionView;
    private PostAdapter postAdapter;
    private ArrayList<Post> postData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        fillData();

        rvSuggestionView = (RecyclerView) view.findViewById(R.id.rvSuggestion);
        postAdapter = new PostAdapter(getActivity(), postData);
        rvSuggestionView.setAdapter(postAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvSuggestionView.setLayoutManager(postLayoutManager);

        return view;
    }

    private void fillData(){
        postData = new ArrayList<>();

        Post data1 = new Post("SuggestionID",
                "usedID",
                getResources().getString(R.string.sample_name1),
                getResources().getString(R.string.sample_time),
                "Some dummy content",
                getResources().getDrawable(R.drawable.profilepicture1));
        postData.add(data1);

        Post data2 = new Post("SuggestionID",
                "usedID",
                getResources().getString(R.string.sample_name2),
                getResources().getString(R.string.sample_time),
                "Some dummy content",
                getResources().getDrawable(R.drawable.profilepicture2));
        postData.add(data2);

        Post data3 = new Post("SuggestionID",
                "usedID",
                getResources().getString(R.string.sample_name3),
                getResources().getString(R.string.sample_time),
                "Some dummy content some dummy content some dummy content some dummy content",
                getResources().getDrawable(R.drawable.profilepicture3));
        postData.add(data3);

        Post data4 = new Post("SuggestionID",
                "usedID",
                getResources().getString(R.string.sample_name4),
                getResources().getString(R.string.sample_time),
                "Some dummy content",
                getResources().getDrawable(R.drawable.profilepicture4));
        postData.add(data4);

        Post data5 = new Post("SuggestionID",
                "usedID",
                getResources().getString(R.string.sample_name5),
                getResources().getString(R.string.sample_time),
                "Some dummy content some dummy content some dummy content some dummy content",
                getResources().getDrawable(R.drawable.profilepicture5));
        postData.add(data5);
    }
}