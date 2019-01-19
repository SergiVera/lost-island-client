using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Chest : MonoBehaviour
{
    public GameObject dialogBox;
    public Text dialogText;
    public string dialog;
    public bool playerInRange;
    public Joybutton joybutton;

    public Item contents;
    public bool isOpen;
    public Signal raiseItem;
    private Animator anim;
    public Inventory playerInventory;
    // Start is called before the first frame update
    void Start()
    {
        anim = GetComponent<Animator>();
        joybutton = FindObjectOfType<Joybutton>();
    }

    // Update is called once per frame
    void Update()
    {
        if ((Input.GetKeyDown(KeyCode.Space) || joybutton.Pressed) && playerInRange )
        {
            if (!isOpen){
                OpenChest();
            }
            else
            {
                ChestAlreadyOpen();
            }
           
        }

    }
    public void OpenChest()
    {
        //dialog window on
        dialogBox.SetActive(true);
        //dialog text = contents text
        dialogText.text = contents.itemDescription;
        //add contents to the inventory
        playerInventory.AddItem(contents);
        playerInventory.currentItem = contents;
        //raise the signal to the player to animate
        raiseItem.Raise();
        //set the chest to opened
        isOpen = true;
        anim.SetBool("opened", true);


    }
    public void ChestAlreadyOpen()
    {
            //Dialog off
            dialogBox.SetActive(false);
            //raise the signal to the player to stop animating
            raiseItem.Raise();

    }
    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player") && !other.isTrigger && !isOpen)
        {
            playerInRange = true;
        }
    }

    private void OnTriggerExit2D(Collider2D other)
    {
        if (other.CompareTag("Player") && !other.isTrigger && !isOpen)
        {
            playerInRange = false;
            dialogBox.SetActive(false);
        }
    }

}
