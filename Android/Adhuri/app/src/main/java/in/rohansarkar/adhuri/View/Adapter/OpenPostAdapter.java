package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.OpenPost;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class OpenPostAdapter extends RecyclerView.Adapter<OpenPostAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<OpenPost> postData;
    private LayoutInflater inflater;
    private HomeInterface homeInterface;

    public OpenPostAdapter(Context context, HomeInterface homeInterface, ArrayList<OpenPost> postData) {
        this.context = context;
        this.homeInterface = homeInterface;
        this.postData = postData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public OpenPostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_open_post_feed, parent, false);
        OpenPostAdapter.MyViewHolder holder = new OpenPostAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(OpenPostAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvName.setText(postData.get(position).getName());
        myViewHolder.ivProfilePic.setBackground(postData.get(position).getProfilePic());
        myViewHolder.tvTag.setText(postData.get(position).getTags());
        myViewHolder.tvTime.setText(postData.get(position).getTime());
        myViewHolder.tvContent.setText(postData.get(position).getContent());
        myViewHolder.tvSuggestionCount.setText(context.getResources().getString(R.string.sample_suggestion_counts));

        View.OnClickListener showOpenPostListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.showOpenPostFragment();
            }
        };
        myViewHolder.tvContent.setOnClickListener(showOpenPostListener);
        myViewHolder.tvSuggestionCount.setOnClickListener(showOpenPostListener);

        View.OnClickListener showProfileListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeInterface.showProfileFragment();
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
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivCircularImage);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvSuggestionCount = (TextView) itemView.findViewById(R.id.tvSuggestionCount);
        }
    }
}