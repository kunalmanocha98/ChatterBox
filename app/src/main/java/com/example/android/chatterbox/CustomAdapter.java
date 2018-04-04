package com.example.android.chatterbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kunal on 10-Nov-17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    List<Values> list;
    Context context;
    String user_name;
    public static int left=101,right=102;
    public CustomAdapter(Context context, List<Values> list, String user_name) {
        this.context=context;
        this.list=list;
        this.user_name=user_name;

    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==left){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layoutleft, parent, false);
            ViewHolder vh=new ViewHolder(v);
            return vh;
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layoutright, parent, false);
            ViewHolder vh=new ViewHolder(v);
            return vh;
        }

    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        holder.reg.setText(list.get(position).getRegnumber());
        holder.msg.setText(list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView reg,msg;
        public ViewHolder(View v) {
            super(v);
            reg=(TextView)v.findViewById(R.id.txt_regnumber);
            msg=(TextView)v.findViewById(R.id.txt_message);

        }
    }

    @Override
    public int getItemViewType(int position) {
        String usr=list.get(position).getRegnumber();
        if(usr.equals(user_name)){
            return right;
        }else{
            return left;
        }
    }
}
