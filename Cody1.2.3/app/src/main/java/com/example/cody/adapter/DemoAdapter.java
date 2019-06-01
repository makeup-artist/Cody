package com.example.cody.adapter;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cody.R;
import com.example.cody.entity.BaseEntity;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<BaseEntity> mEntityList;

    public DemoAdapter (Context context, List<BaseEntity> entityList){
        this.mContext = context;
        this.mEntityList = entityList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_photo_items, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        BaseEntity entity = mEntityList.get(position);
        ((DemoViewHolder)holder).mText.setText(entity.getText());
        ((DemoViewHolder)holder).mImage.setImageResource(entity.getImage());
        if(mOnItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    private class DemoViewHolder extends RecyclerView.ViewHolder{

        private TextView mText;
        private ImageView mImage;
        public DemoViewHolder(View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.next_item);
            mImage = (ImageView) itemView.findViewById(R.id.next_image);
        }
    }

    //点击监听
    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
    }
    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener){
        this.mOnItemClickLitener = onItemClickLitener;
    }
}
