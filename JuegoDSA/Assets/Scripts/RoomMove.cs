using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RoomMove : MonoBehaviour {
    public Vector3 cameraChange;
    public Vector3 playerChange;
    private CameraMovement cam;
    public bool needText;
    public string placeName;
    public GameObject text;
    public Text placeText;
    private GameMaster gm;


	// Use this for initialization
	void Start () {
        cam = Camera.main.GetComponent<CameraMovement>();
        gm = GameObject.FindGameObjectWithTag("GM").GetComponent<GameMaster>();
	}
	
	// Update is called once per frame
	void Update () {
		
	}
    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.CompareTag("Player") && !other.isTrigger)
        {
            cam.minPosition += cameraChange;
            cam.maxPosition += cameraChange;

            other.transform.position += playerChange;
            //checkPoint
            Debug.Log("Ahora le diria a la API que estoy en: " + this.placeName);
            gm.lastCheckPointPos = other.transform.position; 
            //gm.lastHealth = 
        }
    }
  //  private void checkPoint()
 
}
