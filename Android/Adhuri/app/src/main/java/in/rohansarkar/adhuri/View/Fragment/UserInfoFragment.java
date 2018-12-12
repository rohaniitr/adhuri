package in.rohansarkar.adhuri.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class UserInfoFragment extends Fragment {
    private HomeInterface homeInterface;
    private Spinner sGender;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeInterface= (HomeInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        setSpinner(view);
        return view;
    }

    private void setSpinner(View view){
        sGender = (Spinner) view.findViewById(R.id.sGender);
        List<String> genders = new ArrayList<String>();
        genders.add("Male");
        genders.add("Female");
        genders.add("Unspecified");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGender.setAdapter(dataAdapter);
    }
}