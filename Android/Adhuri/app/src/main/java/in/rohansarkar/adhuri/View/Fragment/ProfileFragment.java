package in.rohansarkar.adhuri.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.ProfilePostAdapter;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class ProfileFragment extends Fragment {
    private ProfilePostAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context context;
    private HomeInterface homeInterface;
    private FloatingActionButton fabAddPost;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeInterface = (HomeInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        fabAddPost = (FloatingActionButton) view.findViewById(R.id.fabAddPost);
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.showAddPostFragment();
            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.tlPost);
        viewPager = (ViewPager) view.findViewById(R.id.vpPost);

        adapter = new ProfilePostAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OpenPostFragment(), "OPEN");
        adapter.addFragment(new ClosePostFragment(), "CLOSE");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ImageView ivSettings = (ImageView) view.findViewById(R.id.ivSettings);
        ivSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.showUserinfoFragment();
            }
        });

        return view;
    }
}