using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class APIManager : MonoBehaviour
{
    // private GameMaster gm;
    public UserStats userAttributes;
    // Start is called before the first frame update
    void Start()
    {
        //gm = GameObject.FindGameObjectWithTag("GM").GetComponent<GameMaster>();     
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public UserStats getAttributes(int idUser)
    {
        #if UNITY_ANDROID
               AndroidJavaObject attributesUserObject = new AndroidJavaObject("MainActivity");
                UserStats res = attributesUserObject.Call<UserStats>("myStatsCallback",idUser);
                Debug.Log("Current health: " + res.getCurrentHealth());
                Debug.Log("Max health: " + res.getMaxHealth());
                Debug.Log("Attack: " + res.getAttack());
                Debug.Log("CheckPoint: " + res.getCheckPoint());
                Debug.Log("Points: " + res.getPoints());
                Debug.Log("Enemies Killed: " + res.getEnemiesKilled());
                Debug.Log("Level: " + res.getLevel());
                Debug.Log("User ID: " + res.getUser_id());
                /*int cp = res.getCheckPoint();
                if (cp == 1) gm.lastCheckPointPos = new Vector2(5,5); //Checkpoint Playa
                else if(cp == 2) gm.lastCheckPointPos = new Vector2(16, 5);//Checkpoint Tribu
                else if(cp == 3) gm.lastCheckPointPos = new Vector2(35, 5);//Checkpoint Jungla
                */
        //UserStats res = new UserStats(4,8,1,1,28,2,1,idUser);
        return res;
#else

        UserStats user = new UserStats(4, 8, 1, 1, 288, 2, 1, 1);
        return user;
       
#endif

    }
}