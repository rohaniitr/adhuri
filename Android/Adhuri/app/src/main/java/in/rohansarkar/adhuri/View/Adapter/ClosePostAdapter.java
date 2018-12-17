package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.ClosePost;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.View.Interface.HomeInterface;

public class ClosePostAdapter extends RecyclerView.Adapter<ClosePostAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ClosePost> postData;
    private LayoutInflater inflater;
    private CollaboratorAdapter collaboratorAdapter;
    private NavController navController;

    public ClosePostAdapter(Context context, NavController navController, ArrayList<ClosePost> postData) {
        this.context = context;
        this.postData = postData;
        inflater = LayoutInflater.from(context);
        this.navController = navController;
    }
    @Override
    public ClosePostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_close_post_feed, parent, false);
        ClosePostAdapter.MyViewHolder holder = new ClosePostAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ClosePostAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvName.setText(postData.get(position).getName());
        myViewHolder.tvTag.setText(postData.get(position).getTags());
        myViewHolder.tvTime.setText(postData.get(position).getTime());
        myViewHolder.tvContent.setText(postData.get(position).getContent());

        myViewHolder.ivProfilePic.setBackground(postData.get(position).getProfilePic());
        myViewHolder.ivCollaborator[0].setBackground(postData.get(position).getCollaboratorPics().get(0));
        myViewHolder.ivCollaborator[1].setBackground(postData.get(position).getCollaboratorPics().get(1));
        myViewHolder.ivCollaborator[2].setBackground(postData.get(position).getCollaboratorPics().get(2));

        View.OnClickListener showClosePostListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_homeFragment_to_closePostFragment);
            }
        };
        myViewHolder.tvContent.setOnClickListener(showClosePostListener);
    }
    @Override
    public int getItemCount() {
        return postData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvTime, tvTag, tvContent;
        //        RecyclerView rvCollaborators;
        ImageView ivProfilePic;
        ImageView[] ivCollaborator = new ImageView[3];

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivProfilePic = (ImageView) itemView.findViewById(R.id.ivCircularImage);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
//            ivCollaborator[0] = (ImageView) itemView.findViewById(R.id.iCollaborator1).findViewById(R.id.ivCircularImage);
//            ivCollaborator[1] = (ImageView) itemView.findViewById(R.id.iCollaborator2).findViewById(R.id.ivCircularImage);
//            ivCollaborator[2] = (ImageView) itemView.findViewById(R.id.iCollaborator3).findViewById(R.id.ivCircularImage);
//            rvCollaborators = (RecyclerView) itemView.findViewById(R.id.rvCollabotorPic);
        }
    }
}