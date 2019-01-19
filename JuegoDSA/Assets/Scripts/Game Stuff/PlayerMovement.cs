using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public enum PlayerState
{
    walk,
    attack,
    interact,
    stagger,
    idle
}
public class PlayerMovement : MonoBehaviour{

    public float speed;
    public PlayerState currentState;
    private Rigidbody2D myRigidbody;
    private Vector3 change;
    private Animator animator;
    public FloatValue currentHealth;
    //public FloatValue currentAttack;
    public Signal playerHealthSignal;
    private GameMaster gm;
    public Joystick joystick;
    public Joybutton joybutton;
    public APIManager apimanager;
    public Inventory playerInventory;
    public SpriteRenderer receivedItemSprite;

  
	// Use this for initialization
	void Start () {
        currentState = PlayerState.walk;
        myRigidbody = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
        myRigidbody = GetComponent<Rigidbody2D>();
        animator.SetFloat("moveX", 0);
        animator.SetFloat("moveY", -1);
        gm = GameObject.FindGameObjectWithTag("GM").GetComponent<GameMaster>();
        transform.position = gm.lastCheckPointPos;
        joybutton = FindObjectOfType<Joybutton>();
        UserStats userStats = new UserStats();
        apimanager = new APIManager();
        userStats = apimanager.getAttributes(1);
        currentHealth.RuntimeValue = (float)userStats.getCurrentHealth();
        playerHealthSignal.Raise();
    }
	
	// Update is called once per frame
	void Update () {
        //is the player in an interaction
        if(currentState == PlayerState.interact)
        {
            return;
        }
        change = Vector3.zero;
        change.x = joystick.Horizontal;
        change.y = joystick.Vertical;
        /*CONTROLES PC:
         * change.x = Input.GetAxisRaw("Horizontal");
        change.y = Input.GetAxisRaw("Vertical");*/
        if ((Input.GetButtonDown("attack") || joybutton.Pressed) && currentState != PlayerState.attack && currentState != PlayerState.stagger)
        {
            StartCoroutine(AttackCo());
        }
        else if(currentState == PlayerState.walk || currentState == PlayerState.idle)
        {
            UpdateAnimationAndMove();
        }

        if (Input.GetKeyDown(KeyCode.C))
        {
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        }
   
	}
    private IEnumerator AttackCo()
    {
        animator.SetBool("attacking", true);
        currentState = PlayerState.attack;
        yield return null;
        animator.SetBool("attacking" , false);
        yield return new WaitForSeconds(.3f);
        if(currentState != PlayerState.interact)
        {
            currentState = PlayerState.walk;
        }
        
    }
    public void RaiseItem()
    {
        if(playerInventory.currentItem != null)
        {
            if (currentState == PlayerState.attack)
            {
                animator.SetBool("receive item", true);
                currentState = PlayerState.interact;
                receivedItemSprite.sprite = playerInventory.currentItem.itemSprite;
                Debug.Log("Estoy dentro del if");
            }
            else
            {
                animator.SetBool("receive item", false);
                currentState = PlayerState.idle;
                receivedItemSprite.sprite = null;
                playerInventory.currentItem = null;
                Debug.Log("Estoy en el else");
            }
        }
    }
    void UpdateAnimationAndMove()
    {
        if (change != Vector3.zero)
        {
            MoveCharacter();
            animator.SetFloat("moveX", change.x);
            animator.SetFloat("moveY", change.y);
            animator.SetBool("moving", true);

        }
        else
        {
            animator.SetBool("moving", false);
        }
    }
    void MoveCharacter()
    {
        change.Normalize();
        myRigidbody.MovePosition(transform.position + change * speed * Time.deltaTime);
    }
    public void Knock(float knockTime, float damage)
    {
        Debug.Log("Damage knock: " + damage);
        currentHealth.RuntimeValue -= damage;
        gm.lastHealth = currentHealth.RuntimeValue;
        playerHealthSignal.Raise(); //Notificar a todos los que escuchan que ha disminuido la vida
        if (currentHealth.RuntimeValue > 0)
        {
            StartCoroutine(KnockCo(knockTime));
        }
        else
        {
            this.gameObject.SetActive(false);
        }

    }
    private IEnumerator KnockCo(float knockTime)
    {
        if (myRigidbody != null)
        {
            yield return new WaitForSeconds(knockTime);
            myRigidbody.velocity = Vector2.zero;
            currentState = PlayerState.idle;
        }
    }

}
