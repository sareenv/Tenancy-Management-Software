package com.example.fxproject;
import Builder.OccupancyCreator;
import Interface.Constants;
import Model.Address;
import Model.Occupancy;

import java.util.ArrayList;

public class ThreadRun extends Thread{

    private String operation = "";

    public ThreadRun(String operation){
        this.operation = operation;
    }

    public ArrayList<Occupancy> occupancyList = new ArrayList<>();
    public Occupancy occupancy;

    public Address address;

    int bedroom;
    int bathroom;
    double squareFoot;
    int apartmentNumber;
    int unitNumber;
    @Override
    public void run(){

        if(operation.equalsIgnoreCase("display")){
            occupancyList = Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, null);
        }else if(operation.equalsIgnoreCase("createCondo")){
            occupancy = new OccupancyCreator.Builder(bedroom, bathroom , squareFoot, address, unitNumber).build().createRentalUnit();
        }else if(operation.equalsIgnoreCase("createApartment")){
            occupancy = new OccupancyCreator.Builder(bedroom, bathroom , squareFoot, address, apartmentNumber).build().createRentalUnit();
        }else if(operation.equalsIgnoreCase("createHouse")){
            occupancy = new OccupancyCreator.Builder(address).build().createRentalUnit();
        }

    }

    public void homeOccupancy(Address address){
        this.address = address;
    }

    public void condoOccupancy(Address address, int bedroom, int bathroom, double squareFoot, int unitNumber){
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.squareFoot = squareFoot;
        this.unitNumber = unitNumber;
    }

    public void apartmentOccupancy(Address address, int bedroom, int bathroom, double squareFoot, int apartmentNumber){
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.squareFoot = squareFoot;
        this.apartmentNumber = apartmentNumber;
    }

}
