package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.UserClosePostData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.Util;

public class UserClosePostFeedAdapter extends RecyclerView.Adapter<UserClosePostFeedAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserClosePostData> postData;
    private LayoutInflater inflater;
    private NavController navController;

    public UserClosePostFeedAdapter(Context context, NavController navController, ArrayList<UserClosePostData> postData) {
        this.context = context;
        this.navController = navController;
        this.postData = postData;
        inflater = LayoutInflater.from(context);
    }

    public UserClosePostFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_close_post_feed, parent, false);
        UserClosePostFeedAdapter.MyViewHolder holder = new UserClosePostFeedAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(UserClosePostFeedAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvName.setText(postData.get(position).getName());
        myViewHolder.tvTag.setText(postData.get(position).getTags().get(0));
        myViewHolder.tvTime.setText(postData.get(position).getTime());
        myViewHolder.tvContent.setText(postData.get(position).getContent());

        if(postData.get(position).getSuggestorCount()<=0)
            myViewHolder.tvSuggestionCount.setText(String.format(context.getString(R.string.sample_collaborators_0)));
        else if(postData.get(position).getSuggestorCount()==1)
            myViewHolder.tvSuggestionCount.setText(String.format(context.getString(R.string.sample_collaborators_1)));
        else if(postData.get(position).getSuggestorCount()==1)
            myViewHolder.tvSuggestionCount.setText(String.format(context.getString(R.string.sample_collaborators_2),
                    postData.get(position).getSuggestorCount()));

        if(postData.get(position).getSuggestorImages().size()>=1) {
            myViewHolder.ivCollab.setVisibility(View.VISIBLE);
            Picasso.get().load(Util.baseUrl + '/' + postData.get(position).getSuggestorImages().get(0))
                    .into(myViewHolder.ivCollab);
        }
        else
            myViewHolder.ivCollab.setVisibility(View.GONE);

        if(postData.get(position).getImage() != null)
            Picasso.get().load(Util.baseUrl + '/' + postData.get(position).getImage()).into(myViewHolder.ivProfilePic);

        View.OnClickListener showOpenPostListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_homeFragment_to_openPostFragment);
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
        ImageView ivProfilePic, ivCollab;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivProfilePic = itemView.findViewById(R.id.ivCircularImage);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvSuggestionCount = itemView.findViewById(R.id.tvSuggestionCount);
            ivCollab = itemView.findViewById(R.id.ivCircularImage);
        }
    }
}
