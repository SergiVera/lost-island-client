package dsa.eetac.upc.edu;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GameApi {
    String BASE_URL = "http://147.83.7.205:8080/dsaApp/";

    //auth
    @POST("auth/login")
    Call<UserStats> login(User user);

    @POST("auth/deleteaccount")
    void deleteAccount(User user);

    @PUT("auth/logout")
    void logout();

    @PUT("auth/newcredentials")
    Call<Void> newCredentials();

    @POST("auth/auth/sign-up")
    void signUp(User user);

    //user and objects
    @GET("objects/allobjects")
    Call<List<GameObject>> allObjects();

    @GET("users/stats")
    Call<List<Stats>> allstats();

    @GET("users/{idUser}/attributes")
    Call<UserStats> mystats(@Path("idUser") int id_user);

    @GET("users/{idUser}/enemies")
    Call<List<Enemy>>userEnemies(@Path("idUser") int id_user);

    @POST("users/{idUser}/buyobject/{idGameObject}")
    void buyObject(@Path("idUser") int id_user, @Path("idGameObject") int id);

    @DELETE("users/{idUser}/sellobject/{idGameObject}")
    Call<Void> sellObject(@Path("idUser") int id,@Path("idGameObject") int idGameObj );
}
