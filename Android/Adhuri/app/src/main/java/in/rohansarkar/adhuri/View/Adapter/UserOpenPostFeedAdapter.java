package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.LoginData;
import in.rohansarkar.adhuri.Model.Data.OpenPostData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.PrefUtil;
import in.rohansarkar.adhuri.Util.Util;

public class UserOpenPostFeedAdapter extends RecyclerView.Adapter<UserOpenPostFeedAdapter.MyViewHolder> {

    private String LOG_TAG = this.getClass().getName();
    private Context context;
    private ArrayList<OpenPostData> postData;
    private LayoutInflater inflater;
    private NavController navController;
    private LoginData adminInfo;

    public UserOpenPostFeedAdapter(Context context, NavController navController, ArrayList<OpenPostData> postData) {
        this.context = context;
        this.navController = navController;
        this.postData = postData;
        inflater = LayoutInflater.from(context);
        adminInfo = PrefUtil.getUserInfo(context);
    }
    @Override
    public UserOpenPostFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_open_post_feed, parent, false);
        UserOpenPostFeedAdapter.MyViewHolder holder = new UserOpenPostFeedAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(UserOpenPostFeedAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvName.setText(postData.get(position).getName());
        myViewHolder.tvTag.setText(postData.get(position).getTags().get(0));
        myViewHolder.tvTime.setText(postData.get(position).getTime());
        myViewHolder.tvContent.setText(postData.get(position).getContent());

        if(postData.get(position).getSuggestorCount()<=0)
            myViewHolder.tvSuggestionCount.setText(String.format(context.getString(R.string.sample_suggestion_count_0)));
        else if(postData.get(position).getSuggestorCount()==1)
            myViewHolder.tvSuggestionCount.setText(String.format(context.getString(R.string.sample_suggestion_count_1),
                    postData.get(position).getSuggestorName()));
        else
            myViewHolder.tvSuggestionCount.setText(String.format(context.getString(R.string.sample_suggestion_count_2),
                    postData.get(position).getSuggestorName(), postData.get(position).getSuggestorCount()-1));

        Log.d("asdf", Util.baseUrl + '/' + postData.get(position).getImage());
        if(postData.get(position).getImage() != null)
            Picasso.get().load(Util.baseUrl + '/' + postData.get(position).getImage()).into(myViewHolder.ivImage);

        //Open Post
        View.OnClickListener showOpenPostListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(context.getResources().getString(R.string.PASS_POST), postData.get(position));

                if(postData.get(position).getUserId()==adminInfo.get_id()) {
                    //Open OpenPostEditFragment
                    //navController.navigate(R.id.action_homeFragment_to_openPostFragment, bundle);
                }
                else {
                    navController.navigate(R.id.action_homeFragment_to_openPostFragment, bundle);
                }
            }
        };
        myViewHolder.tvContent.setOnClickListener(showOpenPostListener);
        myViewHolder.tvSuggestionCount.setOnClickListener(showOpenPostListener);

        //Open Profile
        View.OnClickListener showProfileListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(context.getResources().getString(R.string.USER_ID), postData.get(position).getUserId());
                navController.navigate(R.id.action_homeFragment_to_profileFragment, bundle);
            }
        };
        myViewHolder.tvName.setOnClickListener(showProfileListener);
        myViewHolder.ivImage.setOnClickListener(showProfileListener);

        //DeletePost
        if(postData.get(position).getUserId().equals(adminInfo.get_id())) {
            myViewHolder.ivDeletePost.setVisibility(View.VISIBLE);
            myViewHolder.ivDeletePost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else{
            myViewHolder.ivDeletePost.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return postData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvTime, tvTag, tvContent, tvSuggestionCount;
        ImageView ivImage, ivDeletePost;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivCircularImage);
            ivDeletePost = itemView.findViewById(R.id.ivDeletePost);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvSuggestionCount = itemView.findViewById(R.id.tvSuggestionCount);
        }
    }
}
