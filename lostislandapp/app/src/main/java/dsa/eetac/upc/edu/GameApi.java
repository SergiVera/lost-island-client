package dsa.eetac.upc.edu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GameApi {
    String BASE_URL = "http://147.83.7.155:8080/dsaApp/";

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
    Call<List<GameObject>> allObjects();
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
     *
     * @param id_user
     * @return
     */
    @GET("users/{idUser}/enemies")
    Call<List<Enemy>>userEnemies(@Path("idUser") int id_user);

    @DELETE("users/{idUser}/sellobject/{idGameObject}")
    Call<Void> sellObject(@Path("idUser") int id,@Path("idGameObject") int idGameObj);
}
