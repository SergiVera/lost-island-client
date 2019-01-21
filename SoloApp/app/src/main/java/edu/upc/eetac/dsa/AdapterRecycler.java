package edu.upc.eetac.dsa;

import android.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    public Context context;
    public List<GameObject> data;
    public int id;
    private GameApi myapirest;
    TextView moneyText;

    Callback<Void> buyObjectCall = new Callback<Void>() {

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
               Log.i("comprado!", response.message());

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder
                        .setTitle("comprado!")
                        .setCancelable(false)
                        .setMessage(response.message());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                Log.i("estoy en el else", response.message());
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Log.e("ha fallado!", t.getMessage());

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder
                    .setTitle("comprado!")
                    .setCancelable(false)
                    .setMessage(t.getMessage());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
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
        holder.name.setText(obj.getName());
        holder.type.setText(obj.getType());
        holder.points.setText(Integer.toString(obj.getObjectPoints()));
        holder.cost.setText(Integer.toString(obj.getCost()));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyItem(id,obj.getId());
            }
        });
        Picasso.with(context).load("147.83.7.155:8080/resources/"+obj.getName()+".png")
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void buyItem(int id, int idObject){
        myapirest.buyObject(id, idObject).enqueue(buyObjectCall);
    }

}


