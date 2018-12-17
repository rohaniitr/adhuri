package in.rohansarkar.adhuri.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class HomeFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialise(view);
        setupNavigationView();
    }

    private void initialise(View view) {
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
    }
    private void setupNavigationView() {
        if (bottomNavigationView != null) {

            // Select first menu item by default and show Fragment accordingly.
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(1));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }
    protected void selectFragment(MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.action_tags:
                pushFragment(new TagFragment());
                break;
            case R.id.action_home:
                pushFragment(new FeedFragment());
                break;
            case R.id.action_profile:
//                Send data with fragment
//                Bundle args = new Bundle();
//                args.putInt(context.getResources().getString(R.string.CURRENT_TIME), position);
//                navController.navigate(R.id.action_videoListFragment_to_videoPlayerFragment, args);
                pushFragment(new ProfileFragment());
                break;
        }
    }
    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }

}