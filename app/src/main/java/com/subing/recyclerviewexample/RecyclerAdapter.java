package com.subing.recyclerviewexample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

// onClickListener를 만들기 위해 View.OnClickLister 인터페이스 상속
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    // View에 들어갈 Data
    private ArrayList<Data> listData = new ArrayList<>();
    Context context;

    private OnListItemSelectedInterface mListener;
    public ImageView imageView;
    public TextView textView;
    public TextView textView2;

    public RecyclerAdapter(ArrayList<Data> listData, OnListItemSelectedInterface listener) {
        this.listData = listData;
        this.mListener = listener;
    }

    // ClickListener interface 정의
    public interface OnListItemSelectedInterface{
        void onItemSelected(View view, int position);
    }

    // ViewHolder Class 정의
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        public TextView textView2;

        public ViewHolder(View view) {
            super(view);

            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            this.textView2 = itemView.findViewById(R.id.textView2);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());

                    Log.d("subin", "position = "+ position);
                }
            });

        }
    }

    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Data data = listData.get(position);

        holder.imageView.setImageResource(data.getPhoto());
        holder.textView.setText(data.getTitle());
        holder.textView2.setText(data.getContent());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

}

