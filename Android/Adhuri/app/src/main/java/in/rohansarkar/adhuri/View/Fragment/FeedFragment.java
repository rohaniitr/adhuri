package in.rohansarkar.adhuri.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Adapter.ProfilePostAdapter;

public class FeedFragment extends Fragment implements View.OnClickListener{
    private ProfilePostAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView ivBack;
    private NavController navController;
    private FloatingActionButton fabAddPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAddPost:
                navController.navigate(R.id.action_homeFragment_to_addPostFragment);
                break;
            case R.id.ivBack:
                navController.popBackStack();
                break;
        }
    }

    private void initialise(View view) {
        navController = Navigation.findNavController(view);

        ivBack = view.findViewById(R.id.ivBack);
        fabAddPost = view.findViewById(R.id.fabAddPost);

        fabAddPost.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        tabLayout = view.findViewById(R.id.tlPost);
        viewPager = view.findViewById(R.id.vpPost);

        adapter = new ProfilePostAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new OpenPostFeedFragment(), "OPEN");
        adapter.addFragment(new ClosePostFeedFragment(), "CLOSE");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}