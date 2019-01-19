using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Potion : MonoBehaviour
{
    public GameObject potion;
    public GameObject dialogBox;
    public Text dialogText;
    public string dialog;
    public bool playerInRange;
    public Joybutton joybutton;
 
    void Start()
    {
        joybutton = FindObjectOfType<Joybutton>();
    }
    // Update is called once per frame
    void Update()
    {
        if ((Input.GetKeyDown(KeyCode.Space) || joybutton.Pressed) && playerInRange)
        {
            if (potion.activeInHierarchy)
            {
               potion.SetActive(false);
               dialogBox.SetActive(false);
            }
            else
            {
                potion.SetActive(true);
                dialogBox.SetActive(true);
                dialogText.text = dialog;
            }
        }
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            playerInRange = true;
        }
    }

    private void OnTriggerExit2D(Collider2D other)
    {
        if (other.CompareTag("Player"))
        {
            playerInRange = false;
            //potion.SetActive(false);
        }
    }
}
