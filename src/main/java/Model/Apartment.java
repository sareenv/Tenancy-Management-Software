package Model;
import java.io.Serializable;

public class Apartment extends Occupancy implements Serializable {
    int apartmentNumber;
    int bedroomCount;
    int bathroomCount;
    double squareFootage;
    Address civicAddress;
    public Apartment(int apartmentNumber,
                     int bedroomCount,
                     int bathroomCount,
                     double squareFootage,
                     Address address) {
        this.apartmentNumber = apartmentNumber;
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.squareFootage = squareFootage;
        this.civicAddress = address;

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
        return "Apartment: " + super.getId().toString() + "\n" +
                " Apartment Number=" + apartmentNumber +
                " Bedroom Count=" + bedroomCount +
                " Bathroom Count=" + bathroomCount +
                " SquareFootage=" + squareFootage +
                " CivicAddress=" + civicAddress;
    }
}