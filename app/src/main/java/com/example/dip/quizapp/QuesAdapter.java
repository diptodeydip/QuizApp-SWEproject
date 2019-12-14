package com.example.dip.quizapp;


import android.content.Context;
import android.content.Intent;
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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class QuesAdapter extends RecyclerView.Adapter<QuesAdapter.ImageViewHolder> {
    private Context mContext;
    private List<QuestionFormat> mUploads;
    private OnItemClickListener mListener;

    public QuesAdapter(Context context, List<QuestionFormat> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.queslayout, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        QuestionFormat uploadCurrent = mUploads.get(position);

        holder.a.setText("A : "+uploadCurrent.getOpa());
        holder.b.setText("B : "+uploadCurrent.getOpb());
        holder.c.setText("C : "+uploadCurrent.getOpc());
        holder.d.setText("D : "+uploadCurrent.getOpd());
        holder.q.setText("Q"+"-"+(position+1)+"\n"+uploadCurrent.getQuestion());
        if(MainActivity.userFlag.equalsIgnoreCase("Teacher"))
        holder.ca.setText("Correct Ans : "+uploadCurrent.getCa());

        try {
             if(questionlistforstudent.flag.get(position)==true)
             holder.mark.setVisibility(View.VISIBLE);
        }
        catch (Exception e){};

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnCreateContextMenuListener
            , MenuItem.OnMenuItemClickListener {

        public TextView a,b,c,d,ca,q;
        public ImageView mark;

        public ImageViewHolder(View itemView) {

            super(itemView);
            q = itemView.findViewById(R.id.textView11);
            a = itemView.findViewById(R.id.textView22);
            b = itemView.findViewById(R.id.textView33);
            c = itemView.findViewById(R.id.textView44);
            d = itemView.findViewById(R.id.textView55);
            ca = itemView.findViewById(R.id.textView66);
            mark = itemView.findViewById(R.id.mark);

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
            if(MainActivity.userFlag.equalsIgnoreCase("Teacher")) {
                menu.setHeaderTitle("Select Action");
                MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");
                delete.setOnMenuItemClickListener(this);
            }
            else{
                menu.setHeaderTitle("Select Answer");
                MenuItem A = menu.add(Menu.NONE, 2, 1, "A");
                A.setOnMenuItemClickListener(this);
                MenuItem B = menu.add(Menu.NONE, 3, 2, "B");
                B.setOnMenuItemClickListener(this);
                MenuItem C = menu.add(Menu.NONE, 4, 3, "C");
                C.setOnMenuItemClickListener(this);
                MenuItem D = menu.add(Menu.NONE, 5, 4, "D");
                D.setOnMenuItemClickListener(this);
            }

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();

            switch (item.getItemId()) {
                case 1:

                    mListener.onDeleteClick(position);
                    return true;
                case 2:
                    MainActivity.answer = "A";
                    mListener.onAnswerClick(position);
                    return true;
                case 3:
                    MainActivity.answer = "B";
                    mListener.onAnswerClick(position);
                    return true;
                case 4:
                    MainActivity.answer = "C";
                    mListener.onAnswerClick(position);
                    return true;
                case 5:
                    MainActivity.answer = "D";
                    mListener.onAnswerClick(position);
                    return true;
            }
            return false;
        }


    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onAnswerClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}