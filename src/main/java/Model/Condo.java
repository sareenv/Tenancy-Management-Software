package Model;

import java.io.Serializable;

public class Condo extends Apartment implements Serializable {
    int unitNumber;
    public Condo(int unitNumber,
                 int bedroomCount,
                 int bathroomCount,
                 double squareFootage, Address address) {
        super(unitNumber, bedroomCount, bathroomCount, squareFootage, address);
        this.unitNumber = unitNumber;
    }

    @Override
    public Address getOccupancyAddress() {
        return this.civicAddress;
    }

    @Override
    public void setIsOccupancyAvailable(boolean isAvailable) {
        super.isOccupancyAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Condo " + super.getId().toString() + "\n" +
                " Bedroom Count: " + bedroomCount +
                " Bathroom Count: " + bathroomCount +
                " SquareFootage: " + squareFootage +
                " CivicAddress: " + civicAddress +
                " UnitNumber: " + unitNumber +
                " Availability: " + isOccupancyAvailable;
    }
}