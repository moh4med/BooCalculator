package com.example.mohamed.boocalculator;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;





public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.operationViewHolder> {
    ArrayList<operationanswerdata> OpHistory;
    operationviewOnClickListener mlistener;
    interface operationviewOnClickListener{
        void operationviewOnClick(String operation);
    }
    public HistoryAdapter( ArrayList<operationanswerdata> OperationHistory,operationviewOnClickListener opviewlistener){
        this.OpHistory=OperationHistory;
        mlistener=opviewlistener;
    }
    @Override
    public operationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.operationview, parent, false);
        return new operationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(operationViewHolder holder, int position) {
        final String operation=OpHistory.get(position).operation;
        String ans=OpHistory.get(position).answer;
        holder.anstv.setText(ans);
        holder.optv.setText(operation);
        holder.mcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.operationviewOnClick(operation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OpHistory.size();
    }

    public class operationViewHolder extends RecyclerView.ViewHolder{
        TextView optv;
        TextView anstv;
        CardView mcardview;
        public operationViewHolder(View itemView) {
            super(itemView);
            optv=(TextView) itemView.findViewById(R.id.optv);
            anstv=(TextView) itemView.findViewById(R.id.anstv);
            mcardview=(CardView)itemView.findViewById(R.id.card_view);
        }
    }
}
