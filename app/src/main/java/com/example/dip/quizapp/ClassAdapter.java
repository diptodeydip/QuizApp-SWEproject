package com.example.dip.quizapp;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
//import android.support.annotation.NonNull;
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

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Classes> mUploads;
    private OnItemClickListener mListener;

    public ClassAdapter(Context context, List<Classes> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.classlayout, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Classes uploadCurrent = mUploads.get(position);

        holder.code.setText("ClassCode : "+uploadCurrent.getCode());
        holder.className.setText("("+(position+1)+")"+"Exam Name : "+uploadCurrent.getClassName());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnCreateContextMenuListener
            , MenuItem.OnMenuItemClickListener {

        public TextView code,className;

        public ImageViewHolder(View itemView) {

            super(itemView);

            code = itemView.findViewById(R.id.classcode);
            className = itemView.findViewById(R.id.className);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            //  if (mListener != null) {
            int position = getAdapterPosition();
            //  if (position != RecyclerView.NO_POSITION) {
            mListener.onItemClick(position);
            //}
            // }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();

            switch (item.getItemId()) {
                case 1:
                    mListener.onDeleteClick(position);
                    return true;
            }
            return false;
        }


    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}