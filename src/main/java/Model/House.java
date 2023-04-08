package Model;

public class House extends Occupancy {
    Address civicAddress;
    public House(Address address) {
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
        return "House " + super.getId().toString() + "\n" +
                "civicAddress: " + civicAddress;
    }
}