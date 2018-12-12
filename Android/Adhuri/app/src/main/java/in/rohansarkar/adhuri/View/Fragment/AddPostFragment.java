package in.rohansarkar.adhuri.View.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Tag;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.HorizontalTagsAdapter;

public class AddPostFragment extends Fragment {
    private RecyclerView rvTags;
    private HorizontalTagsAdapter tagsAdapter;
    private ArrayList<Tag> tagData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        fillData();

        rvTags = (RecyclerView) view.findViewById(R.id.rvTags);
        tagsAdapter = new HorizontalTagsAdapter(getActivity(), tagData);
        rvTags.setAdapter(tagsAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTags.setLayoutManager(postLayoutManager);



        return view;
    }

    private void fillData(){
        tagData = new ArrayList<>();

        Tag data1 = new Tag("Poem", true);
        tagData.add(data1);

        Tag data2 = new Tag("Shayari", false);
        tagData.add(data2);

        Tag data3 = new Tag("Philosophy", false);
        tagData.add(data3);

        Tag data4 = new Tag("Quote", false);
        tagData.add(data4);

        Tag data5 = new Tag("Kavita", false);
        tagData.add(data5);
    }
}