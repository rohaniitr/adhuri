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
import in.rohansarkar.adhuri.Model.Data.UserOpenPostData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.Util;

public class UserOpenPostFeedAdapter extends RecyclerView.Adapter<UserOpenPostFeedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserOpenPostData> postData;
    private LayoutInflater inflater;
    private NavController navController;

    public UserOpenPostFeedAdapter(Context context, NavController navController, ArrayList<UserOpenPostData> postData) {
        this.context = context;
        this.navController = navController;
        this.postData = postData;
        inflater = LayoutInflater.from(context);
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
            Picasso.get().load(Util.baseUrl + '/' + postData.get(position).getImage()).into(myViewHolder.ivProfilePic);

        View.OnClickListener showOpenPostListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(context.getResources().getString(R.string.PASS_POST), postData.get(position));
                navController.navigate(R.id.action_homeFragment_to_openPostFragment, bundle);
            }
        };
        myViewHolder.tvContent.setOnClickListener(showOpenPostListener);
        myViewHolder.tvSuggestionCount.setOnClickListener(showOpenPostListener);

        View.OnClickListener showProfileListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_homeFragment_to_profileFragment);
            }
        };
        myViewHolder.tvName.setOnClickListener(showProfileListener);
        myViewHolder.ivProfilePic.setOnClickListener(showProfileListener);
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvTime, tvTag, tvContent, tvSuggestionCount;
        ImageView ivProfilePic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivProfilePic = itemView.findViewById(R.id.ivCircularImage);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvSuggestionCount = itemView.findViewById(R.id.tvSuggestionCount);
        }
    }
}