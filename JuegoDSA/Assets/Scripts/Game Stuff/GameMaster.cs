using System.Collections;
using System.Collections.Generic;
using System.IO;
using UnityEngine;

public class GameMaster : MonoBehaviour {

    private static UserStats userStats;
    private static GameMaster instance;
    public Vector2 lastCheckPointPos;
    public float lastHealth;

    void Awake()
    {
        if(instance == null)
        {
            instance = this;
            DontDestroyOnLoad(instance);
        }
        else
        {
            Destroy(gameObject);
        }
    }
    void Start()
    {

    }
    public bool IsSaveFile()
    {
        return Directory.Exists(Application.persistentDataPath + "/game_save");
    }
    public void SaveGame()
    {
        if (!IsSaveFile())
        {
            Directory.CreateDirectory(Application.persistentDataPath + "/game_save");
        }
    }
}
