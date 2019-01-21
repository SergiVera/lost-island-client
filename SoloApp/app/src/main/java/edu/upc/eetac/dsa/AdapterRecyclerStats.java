package edu.upc.eetac.dsa;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterRecyclerStats extends RecyclerView.Adapter<AdapterRecyclerStats.ViewHolder> {
    private List<Stats> data;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        // public Button btn;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(android.R.id.text1);
            // btn = v.findViewById(android.R.id.button1);
        }
    }

    public AdapterRecyclerStats(List<Stats> data) {
        this.data = data;
    }

    @Override
    public AdapterRecyclerStats.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_selectable_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerStats.ViewHolder holder, int position) {
        Stats obj = data.get(position);
        holder.text.setText("Pos: "+position+" "+ obj.getUsername()+" Points: "+ obj.getPoints());
        holder.itemView.setTag(obj.getPoints());//por ver
        // holder.btn.setText("edit");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}


