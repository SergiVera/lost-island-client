using System.Collections;
using System.Collections.Generic;
using System.Net;
using UnityEngine;
using New

public class APIManager : MonoBehaviour
{
    // private GameMaster gm;
    public string res;
    //private AndroidJavaObject javaClass;

    // Start is called before the first frame update
    void Start()
    {
        //gm = GameObject.FindGameObjectWithTag("GM").GetComponent<GameMaster>();
        //javaClass = new AndroidJavaObject("edu.upc.eetac.dsa.MainActivity");
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public UserAttributes getAttributes()
    {
/*#if UNITY_ANDROID || UNITY_ANDROID_API
        using (AndroidJavaClass javaClass = new AndroidJavaClass("edu.upc.eetac.dsa.ConfigurationActivity"))
        {
            using (AndroidJavaObject activity = javaClass.GetStatic<AndroidJavaObject>("mContext"))
            {
                res = activity.Call<string>("getAttributesUser");
                Debug.Log("Returned string: " + res);
                UserAttributes userAttributes = new UserAttributes();

                using (var webClient = new WebClient())
                {
                    JsonConvert.DeserializeObject<UserAttributes>(res);
                }
                /*Debug.Log("Max health: " + res.getMaxHealth());
                Debug.Log("Attack: " + res.getAttack());
                Debug.Log("CheckPoint: " + res.getCheckPoint());
                Debug.Log("Points: " + res.getPoints());
                Debug.Log("Enemies Killed: " + res.getEnemiesKilled());
                Debug.Log("Level: " + res.getLevel());
                Debug.Log("User ID: " + res.getUser_id());
                /*int cp = res.getCheckPoint();
                if (cp == 1) gm.lastCheckPointPos = new Vector2(5,5); //Checkpoint Playa
                else if(cp == 2) gm.lastCheckPointPos = new Vector2(16, 5);//Checkpoint Tribu
                else if(cp == 3) gm.lastCheckPointPos = new Vector2(35, 5);//Checkpoint Junglass

                UserAttributes user = new UserAttributes(4,8,1,1,28,2,1,1);
                return user;*/
            }
        }
#else

        UserAttributes user = new UserAttributes(8, 10, 1, 1, 288, 2, 1, 1);
        return user;
       
#endif

    }
    }