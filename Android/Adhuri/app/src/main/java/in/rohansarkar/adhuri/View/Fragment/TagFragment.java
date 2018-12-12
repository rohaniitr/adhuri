package in.rohansarkar.adhuri.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Tag;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.TagAdapter;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class TagFragment extends Fragment {
    private RecyclerView rvTagView;
    private TagAdapter tagAdapter;
    private ArrayList<Tag> tagData;
    private ImageView ivBack;
    private HomeInterface homeInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeInterface = (HomeInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        fillData();
        addOnBackListener(view);

        rvTagView = (RecyclerView) view.findViewById(R.id.rvTag);
        tagAdapter = new TagAdapter(getActivity(), tagData);
        rvTagView.setAdapter(tagAdapter);
        LinearLayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        rvTagView.setLayoutManager(postLayoutManager);

        return view;
    }

    private void fillData(){
        tagData = new ArrayList<>();

        Tag data1 = new Tag("Poem", false);
        tagData.add(data1);

        Tag data2 = new Tag("Shayari", true);
        tagData.add(data2);

        Tag data3 = new Tag("Philosophy", true);
        tagData.add(data3);

        Tag data4 = new Tag("Quote", false);
        tagData.add(data4);

        Tag data5 = new Tag("Kavita", true);
        tagData.add(data5);
    }

    private void addOnBackListener(View view){
        ivBack = (ImageView) view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.backPressed();
            }
        });
    }
}