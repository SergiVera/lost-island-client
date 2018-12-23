using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Monkey : Enemy {
    private Rigidbody2D myRigidbody;
    public Transform target;
    public float chaseRadius;
    public float attackRadius;
    public Transform homePosition;
    public Animator anim;

	// Use this for initialization
	void Start () {
        myRigidbody = GetComponent<Rigidbody2D>();
        anim = GetComponent<Animator>();
        target = GameObject.FindWithTag("Player").transform; //El transform  es la parte que contiene la info de localizacion (size, rotation, scale, position). Queremos saber donde esta el target
		
	}
	
	// Update is called once per frame
	void Update () {
        CheckDistance();
	}
    void CheckDistance()
    {
        if(Vector3.Distance(target.position, transform.position)<= chaseRadius && Vector3.Distance(target.position, transform.position)  > attackRadius)
        {
            Vector3 temp = Vector3.MoveTowards(transform.position, target.position, moveSpeed * Time.deltaTime);
            myRigidbody.MovePosition(temp);
        }
    }
}
