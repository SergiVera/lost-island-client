package dsa.eetac.upc.edu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterUserObjects extends RecyclerView.Adapter<AdapterUserObjects.ViewHolder> {
    public Context context;
    private List<GameObject> data;
    public int id;
    private GameApi myapirest;
    TextView moneyText;

    Callback<Void> sellObjectCall = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                Log.i("bought",response.message());
            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {

        }
    };

    Callback<UserAttributes> myStatsCallBack = new Callback<UserAttributes>() {

        @Override
        public void onResponse(Call<UserAttributes> call, Response<UserAttributes> response) {
            if (response.isSuccessful()) {
                UserAttributes data = response.body();
                moneyText.setText("Points: " + data.getPoints());

            } else {
                Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<UserAttributes> call, Throwable t) {

        }
    };

    public void addElements(List<GameObject> elementList, int id) {
        data.addAll(elementList);
        notifyDataSetChanged();
        this.id = id;
        this.myapirest = GameApi.createAPIRest();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView type;
        public TextView points;
        public TextView cost;
        public Button button;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name_txt);
            type = (TextView) v.findViewById(R.id.type_txt);
            points = (TextView) v.findViewById(R.id.points_txt);
            cost = (TextView) v.findViewById(R.id.cost_txt);
            button = v.findViewById(R.id.button);
            moneyText = v.findViewById(R.id.money_txt);
        }
    }

    public AdapterUserObjects(List<GameObject> list) {
        this.data = list;
    }
    public AdapterUserObjects(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public AdapterUserObjects.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new AdapterUserObjects.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterUserObjects.ViewHolder holder, int position) {
        GameObject obj = data.get(position);
        holder.name.setText(obj.getName());
        holder.type.setText(obj.getType());
        holder.points.setText(Integer.toString(obj.getObjectPoints()));
        holder.cost.setText(Integer.toString(obj.getCost()));
        holder.button.setText("Sell");
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellItem(id,obj.getId());
            }
        });
        Picasso.with(context).load("147.83.7.155:8080/resources/"+obj.getName()+".png")
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void sellItem(int id, int idObject){
        myapirest.sellObject(id, idObject).enqueue(sellObjectCall);
       // myapirest.userAttributes(id).enqueue(myStatsCallBack);
    }
}


