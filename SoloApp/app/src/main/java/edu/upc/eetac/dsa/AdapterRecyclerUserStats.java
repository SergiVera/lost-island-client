package edu.upc.eetac.dsa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterRecyclerUserStats extends RecyclerView.Adapter<AdapterRecyclerUserStats.ViewHolder> {
    private List<UserAttributes> data;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        // public Button btn;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(android.R.id.text1);
            // btn = v.findViewById(android.R.id.button1);
        }
    }

    public AdapterRecyclerUserStats(List<UserAttributes> data) {
        this.data = data;
    }

    @Override
    public AdapterRecyclerUserStats.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_selectable_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerUserStats.ViewHolder holder, int position) {
        UserAttributes obj = data.get(position);
        holder.text.setText(obj.toString());
        holder.itemView.setTag(obj.getAttack());//por ver
        // holder.btn.setText("edit");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}


