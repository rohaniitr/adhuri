package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;
import in.rohansarkar.adhuri.View.Utils.CustomAlertAction;
import in.rohansarkar.adhuri.View.Utils.CustomAlertBox;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SuggestionData> suggestionList;
    private LayoutInflater inflater;
    private NavController navController;
    private LoginData adminInfo;
    private EditText etContent;
    private String userId;

    //etContent & userId: required only for EditOpenPost
    public SuggestionAdapter(Context context, NavController navController, ArrayList<SuggestionData> suggestionList,
                             EditText etContent, String userId) {
        this.context = context;
        this.suggestionList = suggestionList;
        inflater = LayoutInflater.from(context);
        this.navController = navController;
        this.etContent = etContent;
        this.userId = userId;
        this.adminInfo = PrefUtil.getUserInfo(context);
    }
    @Override
    public SuggestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_suggestion, parent, false);
        SuggestionAdapter.MyViewHolder holder = new SuggestionAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(SuggestionAdapter.MyViewHolder myViewHolder, final int position) {
        String name = suggestionList.get(position).getName();
        if(name==null)
            name = "Some Name";

        SpannableStringBuilder content = new SpannableStringBuilder(name + " " + suggestionList.get(position).getContent());
        content.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, name.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        myViewHolder.tvTime.setText(suggestionList.get(position).getTime());
        myViewHolder.tvSuggestion.setText(content);
        if(suggestionList.get(position).getImage()!= null)
            Picasso.get().load(Util.baseUrl + '/' + suggestionList.get(position).getImage()).into(myViewHolder.ivProfilePic);

        if(etContent!= null && userId!= null && adminInfo.get_id().equals(userId)){
            //Editable OpenPost
            myViewHolder.elementLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchUseSuggestionAlert(position);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return suggestionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTime, tvSuggestion;
        ImageView ivProfilePic;
        View elementLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivCircularImage);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSuggestion = itemView.findViewById(R.id.tvSuggestion);
            elementLayout = itemView.findViewById(R.id.lSuggestionLayout);
        }
    }

    private void addSuggestion(int position){
        String suggestion =  suggestionList.get(position).getContent();
        int start = Math.max(etContent.getSelectionStart(), 0);
        int end = Math.max(etContent.getSelectionEnd(), 0);
        etContent.getText().replace(Math.min(start, end), Math.max(start, end), suggestion, 0, suggestion.length());
    }
    private void launchUseSuggestionAlert(final int position){
        CustomAlertBox alertBox = new CustomAlertBox();
        Bundle bundle = new Bundle();
        bundle.putInt(context.getString(R.string.alert_title_id), R.string.use_suggestion);
        bundle.putInt(context.getString(R.string.alert_icon_id), R.drawable.icon_add_suggestion);
        bundle.putInt(context.getString(R.string.alert_message_id), R.string.use_suggestion_alert_content);
        bundle.putSerializable(context.getString(R.string.alert_action_object), new CustomAlertAction() {
            @Override
            public void onSuccess() {
                addSuggestion(position);
            }
            @Override
            public void onError() {

            }
        });
        alertBox.setArguments(bundle);
        alertBox.show(((FragmentActivity)context).getSupportFragmentManager(), null);
    }
}