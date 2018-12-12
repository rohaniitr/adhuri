package in.rohansarkar.adhuri.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.rohansarkar.adhuri.Model.Data.Tag;
import in.rohansarkar.adhuri.R;

public class HorizontalTagsAdapter extends RecyclerView.Adapter<HorizontalTagsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Tag> tagData;
    private LayoutInflater inflater;

    public HorizontalTagsAdapter(Context context, ArrayList<Tag> tagData) {
        this.context = context;
        this.tagData = tagData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HorizontalTagsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_horizontal_tags, parent, false);
        HorizontalTagsAdapter.MyViewHolder holder = new HorizontalTagsAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HorizontalTagsAdapter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.tvTag.setText(tagData.get(position).getTag());

        if(tagData.get(position).isSelected()) {
            myViewHolder.lTags.setBackgroundColor(context.getResources().getColor(R.color.blueTint));
            myViewHolder.ivTick.setVisibility(View.VISIBLE);
        }
        else {
            myViewHolder.lTags.setBackgroundColor(context.getResources().getColor(R.color.black));
            myViewHolder.ivTick.setVisibility(View.GONE);
        }

        myViewHolder.tvTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tagData.get(position).isSelected()){
                    for(int i=0; i<tagData.size(); i++){
                        if(tagData.get(i).isSelected()){
                            tagData.get(i).setSelected(false);
                            notifyItemChanged(i);
                            break;
                        }
                    }
                    tagData.get(position).setSelected(true);
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTag;
        ImageView ivTick;
        RelativeLayout lTags;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTag = (TextView) itemView.findViewById(R.id.tvTags);
            ivTick = (ImageView) itemView.findViewById(R.id.ivTick);
            lTags = (RelativeLayout) itemView.findViewById(R.id.lTags);
        }
    }
}