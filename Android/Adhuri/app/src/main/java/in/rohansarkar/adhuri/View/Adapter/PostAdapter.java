package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Post;
import in.rohansarkar.adhuri.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Post> postData;
    private LayoutInflater inflater;

    public PostAdapter(Context context, ArrayList<Post> postData) {
        this.context = context;
        this.postData = postData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_suggestion, parent, false);
        PostAdapter.MyViewHolder holder = new PostAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostAdapter.MyViewHolder myViewHolder, final int position) {
        String name = postData.get(position).getName();
        SpannableStringBuilder content = new SpannableStringBuilder(name + " " + postData.get(position).getContent());
        content.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        myViewHolder.ivProfilePic.setBackground(postData.get(position).getProfilePic());
        myViewHolder.tvTime.setText(postData.get(position).getTime());
        myViewHolder.tvSuggestion.setText(content);
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTime, tvSuggestion;
        ImageView ivProfilePic;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivCircularImage);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvSuggestion = (TextView) itemView.findViewById(R.id.tvSuggestion);
        }
    }
}