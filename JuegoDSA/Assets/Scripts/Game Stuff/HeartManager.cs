using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HeartManager : MonoBehaviour {

    public Image[] hearts;
    public Sprite fullHeart;
    public Sprite halfFullHeart;
    public Sprite emptyHeart;
    public FloatValue heartContainers;
    public FloatValue playerCurrentHealth;
    public APIManager apimanager;

    void Start()
    {
        InitHearts();
    }

    public void InitHearts()
    {
        for (int i = 0; i < heartContainers.initialValue; i++)
        {
            hearts[i].gameObject.SetActive(true);
            Debug.Log("Length of vector hearts: " + hearts.Length);
        }
    }
    public void UpdateHearts()
    {
        Debug.Log("Current Health of UpdateHearts: " + playerCurrentHealth.RuntimeValue);
        float tempHealth = playerCurrentHealth.RuntimeValue/2; // Considero que un corazon = 2
        UserStats userStats = new UserStats();
        apimanager = new APIManager();
        userStats = apimanager.getAttributes(1);
        heartContainers.initialValue = userStats.getMaxHealth()/2;
        Debug.Log("Max Health in HeartManager: " + heartContainers.initialValue);
        for (int i =0; i<heartContainers.initialValue; i++)
        {
            if(i<= tempHealth-1)
            {
                //Full Hearth
                hearts[i].sprite = fullHeart;
            }else if( i >= tempHealth)
            {
                //empty heart
                hearts[i].sprite = emptyHeart;

            }
            else
            {
                // half full heart
                hearts[i].sprite = halfFullHeart;
            }
      
        }
    }
}
