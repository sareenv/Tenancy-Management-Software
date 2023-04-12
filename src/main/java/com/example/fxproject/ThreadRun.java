package com.example.fxproject;
import Builder.OccupancyCreator;
import Interface.Constants;
import Model.*;

import java.util.ArrayList;
import java.util.Date;

// Thread Run class that implements the Core Thread class from JAVA
public class ThreadRun extends Thread{

    private String operation = "";
    private Administrator administrator = Administrator.makeSharedInstance();

    public ThreadRun(String operation){
        this.operation = operation;
    }

    public ArrayList<Occupancy> occupancyList = new ArrayList<>();
    public ArrayList<Lease> allLeases = new ArrayList<>();
    public ArrayList<Tenant> allTenants = new ArrayList<>();
    public Occupancy occupancy;

    public Address address;

    int bedroom;
    int bathroom;
    double squareFoot;
    int apartmentNumber;
    int unitNumber;

    Lease lease = null;
    Occupancy selectedOccupancy = null;
    Date startDate,endDate;
    Tenant tenant;

    String name,occupation;
    Double income;
    boolean result;

    // uses the different business logic on the basis of creation of thread object, which is defined by 'operation' type.
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
        }else if(operation.equalsIgnoreCase("displayLease")){
            allLeases = Lease.getAllLease(Constants.LeasePath);
        }else if(operation.equalsIgnoreCase("displayTenant")){
            allTenants = Tenant.getAllTenants();
        }else if(operation.equalsIgnoreCase("createLease")){
            lease = new Lease(selectedOccupancy, tenant, startDate, endDate);
        }else if(operation.equalsIgnoreCase("createTenant")){
            result = administrator.addTenant(name, occupation, income);
        }
    }

    // initialzing the variables required for the operation for adding tenant
    public void createTenant(String name, String occupation, Double income){
        this.name = name;
        this.occupation = occupation;
        this.income = income;
    }

    // intialzing the variables reqiured for the operation for creating leases
    public void createLease(Occupancy occupancy, Tenant tenant, Date startDate, Date endDate){
        this.selectedOccupancy = occupancy;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // intialzing the variables reqiured for the operation for creating home
    public void homeOccupancy(Address address){
        this.address = address;
    }

    // intialzing the variables reqiured for the operation for creating condo
    public void condoOccupancy(Address address, int bedroom, int bathroom, double squareFoot, int unitNumber){
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.squareFoot = squareFoot;
        this.unitNumber = unitNumber;
    }

    // intialzing the variables reqiured for the operation for creating apartment
    public void apartmentOccupancy(Address address, int bedroom, int bathroom, double squareFoot, int apartmentNumber){
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.squareFoot = squareFoot;
        this.apartmentNumber = apartmentNumber;
    }

}