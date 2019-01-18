package dsa.eetac.upc.edu;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    public Context context;
    public List<GameObject> data;

    public void addElements(List<GameObject> elementList) {
        data.addAll(elementList);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public Button button;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.textView);
            button = v.findViewById(R.id.button);
        }
    }

    public AdapterRecycler(List<GameObject> list) {
        this.data = list;
    }
    public AdapterRecycler(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public AdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecycler.ViewHolder holder, int position) {
        GameObject obj = data.get(position);
        holder.text.setText(obj.getName());
        holder.button.setText("BUY");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}


