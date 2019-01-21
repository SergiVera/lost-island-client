package edu.upc.eetac.dsa;

class GameObject {
    String type;
    String name;
    int objectPoints;
    int cost;
    int id;

    public GameObject(){ }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectPoints() {
        return objectPoints;
    }

    public void setObjectPoints(int objectPoints) {
        this.objectPoints = objectPoints;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
