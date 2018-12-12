package in.rohansarkar.adhuri.View.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Fragment.AddPostFragment;
import in.rohansarkar.adhuri.View.Fragment.ClosePostFragment;
import in.rohansarkar.adhuri.View.Fragment.HomeFragment;
import in.rohansarkar.adhuri.View.Fragment.PostFragment;
import in.rohansarkar.adhuri.View.Fragment.ProfileFragment;
import in.rohansarkar.adhuri.View.Fragment.UserInfoFragment;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class HomeActivity extends AppCompatActivity implements HomeInterface {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Fragment homeFragment, openPostFragment, closePostFragment, profileFragment;
    private UserInfoFragment userInfoFragment;
    private AddPostFragment addPostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.vContainer, homeFragment);
        transaction.commit();
    }

    private void init(){
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        openPostFragment = new PostFragment();
        closePostFragment = new ClosePostFragment();
        profileFragment = new ProfileFragment();
        userInfoFragment = new UserInfoFragment();
        addPostFragment = new AddPostFragment();
    }

    @Override
    public void showOpenPostFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.vContainer, openPostFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void removeOpenPostFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.remove(openPostFragment);
        transaction.commit();
    }

    @Override
    public void showClosePostFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.vContainer, closePostFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void removeClosePostFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.remove(closePostFragment);
        transaction.commit();
    }

    @Override
    public void showProfileFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.vContainer, profileFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void removeProfileFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.remove(profileFragment);
        transaction.commit();
    }

    @Override
    public void showUserinfoFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.vContainer, userInfoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void showAddPostFragment() {
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.vContainer, addPostFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void backPressed() {
        onBackPressed();
    }
}