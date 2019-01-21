package edu.upc.eetac.dsa;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GameApi {
    String BASE_URL = "http://147.83.7.155:8080/dsaApp/";

    static GameApi createAPIRest() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(GameApi.class);
    }

    /**
     * Return the userattributes of the user if the login is successful
     * @param user User class
     * @return UserAttributes class
     */
    @POST("auth/login")
    Call<UserAttributes> login(@Body User user);
    /**
     * Delete the account of a user
     * @param user
     */
    @POST("auth/deleteaccount")
    Call<Void> deleteAccount(@Body User user);
    /**
     * Logout the account when the user is connected
     * @param idUser ID of the User to logout the session
     */
    @PUT("auth/logout")
    Call<Void> logout(@Body int idUser);
    /**
     * @param credentials Credentials class
     * @return Credentials class
     */
    @PUT("auth/newcredentials")
    Call<Credentials> newCredentials(@Body Credentials credentials);
    /**
     * Creates a new account
     * @param user User class
     */
    @POST("auth/sign-up")
    Call<Void> signUp(@Body User user);
    /**
     * Get all objects in the game
     * @return List of GameObject
     */
    @GET("objects/allobjects")
    Call<List<GameObject>> getAllObjects();
    /**
     * Get the scoreboard about all users
     * @return List of Stats
     */
    @GET("users/stats")
    Call<List<Stats>> allStats();
    /**
     * Get attributes of a user
     * @param idUser ID of the user
     * @return UserAttributes class
     */
    @GET("users/{idUser}/attributes")
    Call<UserAttributes> userAttributes(@Path("idUser") int idUser);
    /**
     * Buy an object from the shop
     * @param idUser ID of the user
     * @param idGameObject ID of the object
     */
    @POST("users/{idUser}/buyobject/{idGameObject}")
    Call<Void> buyObject(@Path("idUser") int idUser, @Path("idGameObject") int idGameObject);
    /**
     * Delete a part of the antenna. This function is only called by Unity
     * @param idUser ID of the user
     * @param idGameObject ID of the object
     */
    @DELETE("users/{idUser}/delete-antenna-part/{idGameObject}")
    Call<Void> deleteAntennaPart(@Path("idUser") int idUser, @Part("idGameObject") int idGameObject);

    /**
     * Delete an enemy of the user. This function is only called by Unity
     * @param idUser ID of the user
     * @param idEnemy ID of the enemy
     */
    @DELETE("users/{idUser}/delete-enemy/{idEnemy}")
    Call<Void> deleteEnemyUser(@Path("idUser") int idUser, @Path("idEnemy") int idEnemy);

    /**
     * Get all remaining enemies of a user. This function is only called by Unity
     * @param idUser ID of the user
     * @return List of Enemy
     */
    @GET("users/{idUser}/enemies")
    Call<List<Enemy>> getEnemiesUser(@Path("idUser") int idUser);

    /**
     * Finish the game of the player. This function is only called by Unity
     * @param idUser ID of the user
     */
    @PUT("users/{idUser}/finishgame")
    Call<Void> finishUserGame(@Path("idUser") int idUser);

    /**
     * Update the attributes of the user when user grabs a poison from the floor. This function is only called by Unity
     * @param idUser ID of the user
     * @param idGameObject ID of the object
     */
    @PUT("users/{idUser}/modifyattributes/{idGameObject}")
    Call<Void> updateUserAttributes(@Path("idUser") int idUser, @Path("idGameObject") int idGameObject);

    /**
     * Get all objects of a user
     * @param idUser ID of the user
     * @return List of GameObject
     */
    @GET("users/{idUser}/objects")
    Call<List<GameObject>> getAllObjectsUser(@Path("idUser") int idUser);

    /**
     * Sell an object of a user
     * @param id ID of the user
     * @param idGameObject ID of the object
     */
    @DELETE("users/{idUser}/sellobject/{idGameObject}")
    Call<Void> sellObject(@Path("idUser") int id, @Path("idGameObject") int idGameObject);

    /**
     * Get the stats of a user
     * @param idUser ID of the user
     * @return Stats class
     */
    @GET("users/{idUser}/stats")
    Call<Stats> getUserStats(@Path("idUser") int idUser);

    /**
     * Update the life of the enemy of a user. This function is only called by Unity
     * @param idUser ID of the user
     * @param idEnemy ID of the enemy
     * @param enemyLife integer of life of the enemy to update
     */
    @PUT("users/{idUser}/update-enemy/{idEnemy}/{enemyLife}")
    Call<Void> updateEnemyUser(@Path("idUser") int idUser, @Path("idEnemy") int idEnemy, @Path("enemyLife") int enemyLife);

    /**
     * Update the currentHealth of a user. This function is only called by Unity
     * @param idUser ID of the user
     * @param currentHealth integer of currentHealth of a user
     * @return
     */
    @PUT("users/{idUser}/updatecurrenthealth/{currentHealth}")
    Call<Void> updateCurrentHealthUser(@Path("idUser") int idUser, @Path("currentHealth") int currentHealth);

    /**
     * Update the killed enemies of a user. This function is only called bu Unity
     * @param idUser ID of the user
     * @param enemiesKilled integer of enemiesKilled of a user
     */
    @PUT("users/{idUser}/updatekilledenemies/{enemiesKilled}")
    Call<Void> updateKilledEnemiesUser(@Path("idUser") int idUser, @Path("enemiesKilled") int enemiesKilled);

    /**
     * Update the points of the user. This function is only called by Unity
     * @param idUser ID of the user
     * @param points integer of points of the user
     */
    @PUT("users/{idUser}/updatepoints/{points}")
    Call<Void> updatePointsUser(@Path("idUser") int idUser, @Path("points") int points);

    /**
     * Update the status of the user (the where the user is in). This function is only called by Unity
     * @param idUser ID of the user
     * @param idGameMap ID of the map
     */
    @PUT("maps/{idUser}/savestatus/{idGameMap}")
    Call<Void> updateStatusUser(@Path("idUser") int idUser, @Path("idGameMap") int idGameMap);

}
