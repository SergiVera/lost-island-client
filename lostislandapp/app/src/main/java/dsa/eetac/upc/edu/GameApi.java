package dsa.eetac.upc.edu;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GameApi {
    String BASE_URL = "http://147.83.7.205:8080/dsaApp/";

    //
    @GET("objects/allobjects")
    Call<List<GameObject>> allObjects();

    @GET("users/stats")
    Call<List<Stats>> allstats();

    @GET("users/{idUser}/attributes")
    Call<UserStats> mystats(@Path("idUser") int id_user);
}
