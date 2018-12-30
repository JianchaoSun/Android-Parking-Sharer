package com.example.jianchaosun.parkingspotsharer;

import java.util.PriorityQueue;

public class parkingLot {
    private String name;
    private PriorityQueue<obj> avaList;

    public parkingLot(){
        avaList = new PriorityQueue<obj>();
    }

    public void addNewParkingObj(obj o){
        avaList.add(o);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
