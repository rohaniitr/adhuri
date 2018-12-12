package in.rohansarkar.adhuri.View.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.ClosePost;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.ClosePostAdapter;

public class ClosePostFragment extends Fragment {
    private RecyclerView rvPostView;
    private ClosePostAdapter postAdapter;
    private ArrayList<ClosePost> postData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        fillData();

        rvPostView = (RecyclerView) view.findViewById(R.id.recycler_view);
        postAdapter = new ClosePostAdapter(getActivity(), postData);
        rvPostView.setAdapter(postAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvPostView.setLayoutManager(postLayoutManager);

        return view;
    }

    private void fillData(){
        postData = new ArrayList<>();

        ArrayList<Drawable> collaboratorPics1 = new ArrayList<>();
        collaboratorPics1.add(getResources().getDrawable(R.drawable.profilepicture2));
        collaboratorPics1.add(getResources().getDrawable(R.drawable.profilepicture3));
        collaboratorPics1.add(getResources().getDrawable(R.drawable.profilepicture4));

        ClosePost data1 = new ClosePost(getResources().getString(R.string.sample_name1),
                getResources().getDrawable(R.drawable.profilepicture1),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_1),
                collaboratorPics1);
        postData.add(data1);

        ArrayList<Drawable> collaboratorPics2 = new ArrayList<>();
        collaboratorPics2.add(getResources().getDrawable(R.drawable.profilepicture2));
        collaboratorPics2.add(getResources().getDrawable(R.drawable.profilepicture3));
        collaboratorPics2.add(getResources().getDrawable(R.drawable.profilepicture4));

        ClosePost data2 = new ClosePost(getResources().getString(R.string.sample_name2),
                getResources().getDrawable(R.drawable.profilepicture2),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_2),
                collaboratorPics2);
        postData.add(data2);

        ArrayList<Drawable> collaboratorPics3 = new ArrayList<>();
        collaboratorPics3.add(getResources().getDrawable(R.drawable.profilepicture2));
        collaboratorPics3.add(getResources().getDrawable(R.drawable.profilepicture3));
        collaboratorPics3.add(getResources().getDrawable(R.drawable.profilepicture4));

        ClosePost data3 = new ClosePost(getResources().getString(R.string.sample_name3),
                getResources().getDrawable(R.drawable.profilepicture3),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_3),
                collaboratorPics3);
        postData.add(data3);

        ArrayList<Drawable> collaboratorPics4 = new ArrayList<>();
        collaboratorPics4.add(getResources().getDrawable(R.drawable.profilepicture2));
        collaboratorPics4.add(getResources().getDrawable(R.drawable.profilepicture3));
        collaboratorPics4.add(getResources().getDrawable(R.drawable.profilepicture4));

        ClosePost data4 = new ClosePost(getResources().getString(R.string.sample_name4),
                getResources().getDrawable(R.drawable.profilepicture4),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_1),
                collaboratorPics4);
        postData.add(data4);

        ArrayList<Drawable> collaboratorPics5 = new ArrayList<>();
        collaboratorPics5.add(getResources().getDrawable(R.drawable.profilepicture2));
        collaboratorPics5.add(getResources().getDrawable(R.drawable.profilepicture3));
        collaboratorPics5.add(getResources().getDrawable(R.drawable.profilepicture4));

        ClosePost data5 = new ClosePost(getResources().getString(R.string.sample_name5),
                getResources().getDrawable(R.drawable.profilepicture5),
                getResources().getString(R.string.sample_time),
                getResources().getString(R.string.sample_tag),
                getResources().getString(R.string.samples_content_3),
                collaboratorPics5);
        postData.add(data5);
    }
}