package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Tag;
import in.rohansarkar.adhuri.R;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Tag> tagData;
    private LayoutInflater inflater;

    public TagAdapter(Context context, ArrayList<Tag> tagData) {
        this.context = context;
        this.tagData = tagData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TagAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_tag, parent, false);
        TagAdapter.MyViewHolder holder = new TagAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TagAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvTag.setText(tagData.get(position).getTag());

        if(tagData.get(position).isSelected())
            myViewHolder.ivSelected.setVisibility(View.VISIBLE);
        else
            myViewHolder.ivSelected.setVisibility(View.GONE);

        myViewHolder.tvTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tagData.get(position).toggleSelected();
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTag;
        ImageView ivSelected;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivSelected = (ImageView) itemView.findViewById(R.id.ivSelected);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);
        }
    }
}