package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import in.rohansarkar.adhuri.R;

public class CollaboratorAdapter extends RecyclerView.Adapter<CollaboratorAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Drawable> collaboratorPics;
    private LayoutInflater inflater;

    public CollaboratorAdapter(Context context, ArrayList<Drawable> collaboratorPics) {
        this.context = context;
        this.collaboratorPics = collaboratorPics;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CollaboratorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        Log.d("Riya", "onCreateViewHolder : " + position);
        View view = inflater.inflate(R.layout.circular_image_view_28, parent, false);
        CollaboratorAdapter.MyViewHolder holder = new CollaboratorAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CollaboratorAdapter.MyViewHolder myViewHolder, final int position) {
        Log.d("Riya", "onBindViewHolder : " + position);
        myViewHolder.ivCollaborator.setBackground(collaboratorPics.get(position));
    }

    @Override
    public int getItemCount() {
        return collaboratorPics.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCollaborator;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivCollaborator = (ImageView) itemView.findViewById(R.id.ivCircularImage);
        }
    }
}