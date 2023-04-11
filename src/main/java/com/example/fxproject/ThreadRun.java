package com.example.fxproject;
import Builder.OccupancyCreator;
import Interface.Constants;
import Model.Address;
import Model.Administrator;
import Model.Occupancy;

import java.util.ArrayList;

public class ThreadRun extends Thread{

    public ArrayList<Occupancy> occupancyList = new ArrayList<>();
    @Override
    public void run(){
        occupancyList = Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
    }
}
