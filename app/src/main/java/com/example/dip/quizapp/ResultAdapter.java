package com.example.dip.quizapp;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.squareup.picasso.Picasso;


import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ImageViewHolder> {
    private Context mContext;
    private List<resultformat> mUploads;


    public ResultAdapter(Context context, List<resultformat> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultlayout, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        resultformat uploadCurrent = mUploads.get(position);
        holder.marks.setText("Marks : "+uploadCurrent.getMarks());
        holder.reg.setText("Reg No : "+uploadCurrent.getReg());
        holder.rank.setText("Rank "+Integer.toString(position+1));
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView reg, rank , marks;

        public ImageViewHolder(View itemView) {

            super(itemView);

            reg = itemView.findViewById(R.id.regNo1);
            marks = itemView.findViewById(R.id.Marks);
            rank = itemView.findViewById(R.id.rank);
        }
    }


}