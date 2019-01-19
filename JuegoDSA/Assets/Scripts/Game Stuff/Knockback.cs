using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Knockback : MonoBehaviour {

    public float thrust;//Fuerza del personaje que ejerce sobre el enemigo
    public float knockTime;
    public float damage;

    public APIManager apimanager;

    private void OnTriggerEnter2D(Collider2D other)
    {
        /*if (other.gameObject.CompareTag("breakable") && other.gameObject.CompareTag("Player")
        {
           // other.GetComponent<pot>().Smash();
        }*/
        if (other.gameObject.CompareTag("Enemy") || other.gameObject.CompareTag("Player")){
            Rigidbody2D hit = other.GetComponent<Rigidbody2D>();
            if(hit != null)
            {
                Vector2 difference = hit.transform.position - transform.position;
                difference = difference.normalized * thrust;
                hit.AddForce(difference, ForceMode2D.Impulse);
                if (other.gameObject.CompareTag("Enemy") && other.isTrigger)
                {
                    UserStats userStats = new UserStats();
                    apimanager = new APIManager();
                    userStats = apimanager.getAttributes(1);
                    damage = userStats.getAttack();
                    hit.GetComponent<Enemy>().currentState = EnemyState.stagger;
                    other.GetComponent<Enemy>().Knock(hit, knockTime,damage);
                }
                if (other.gameObject.CompareTag("Player"))
                {
                    if (other.GetComponent<PlayerMovement>().currentState != PlayerState.stagger)
                    {
                        hit.GetComponent<PlayerMovement>().currentState = PlayerState.stagger;
                        other.GetComponent<PlayerMovement>().Knock(knockTime, damage);
                    }
                }
                
            }
        }
    }

}
