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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.navigation.NavController;
import in.rohansarkar.adhuri.Model.Data.SuggestionData;
import in.rohansarkar.adhuri.R;
import in.rohansarkar.adhuri.Util.Util;

public class OpenPostAdapter extends RecyclerView.Adapter<OpenPostAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SuggestionData> suggestionList;
    private LayoutInflater inflater;
    private NavController navController;

    public OpenPostAdapter(Context context, NavController navController, ArrayList<SuggestionData> suggestionList) {
        this.context = context;
        this.suggestionList = suggestionList;
        inflater = LayoutInflater.from(context);
        this.navController = navController;
    }
    @Override
    public OpenPostAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflater.inflate(R.layout.element_suggestion, parent, false);
        OpenPostAdapter.MyViewHolder holder = new OpenPostAdapter.MyViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(OpenPostAdapter.MyViewHolder myViewHolder, final int position) {
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
    }
    @Override
    public int getItemCount() {
        return suggestionList.size();
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