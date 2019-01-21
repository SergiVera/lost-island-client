using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Barrera3 : MonoBehaviour
{
    public Inventory playerInventory;
    public GameObject barrera;

    // Update is called once per frame
    void Update()
    {

        if (playerInventory.numberOfKeys >= 3)
        {
            if (barrera.activeInHierarchy)
            {
                barrera.SetActive(false);
                Debug.Log("Barrera quitada");
            }
            else
            {
                barrera.SetActive(true);
                Debug.Log("barrera activa");
            }
        }


    }
}