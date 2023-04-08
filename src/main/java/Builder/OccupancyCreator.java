package Builder;

import Model.*;

public class OccupancyCreator {

    private Address address;
    private Integer unitNumber;
    private Integer bedroomCount;
    private Integer bathroomCount;
    private Double squareFootage;
    private String type;
    private Integer apartmentNumber;

    private OccupancyCreator(Builder builder) {
        address = builder.address;
        bathroomCount = builder.bathroomCount;
        bedroomCount = builder.bedroomCount;
        unitNumber = builder.unitNumber;
        squareFootage = builder.squareFootage;
        type = builder.type;
        apartmentNumber = builder.apartmentNumber;
    }


    public static class Builder {
        private Address address;
        private Integer unitNumber;
        private Integer bedroomCount;
        private Integer bathroomCount;
        private Double squareFootage;
        private String type;
        private Integer apartmentNumber;


        // Apt Builder. - type 3;
        public Builder(Integer bedroomCount,
                       Integer bathroomCount,
                       Double squareFootage,
                       Address address,
                       Integer apartmentNumber) {
            this.bedroomCount = bedroomCount;
            this.bathroomCount = bathroomCount;
            this.squareFootage = squareFootage;
            this.address = address;
            this.apartmentNumber = apartmentNumber;
            this.type = "3";
        }

        // Condo Builder - type 1
        public Builder(Integer unitNumber,
                       Integer bathroomCount,
                       Integer bedroomCount,
                       Double squareFootage,
                       Address address) {
            this.type = "1";
            this.unitNumber = unitNumber;
            this.bathroomCount = bathroomCount;
            this.squareFootage = squareFootage;
            this.address = address;
            this.bedroomCount = bedroomCount;
        }

        // House Builder - type 2
        public Builder(Address address) {
            this.address = address;
            this.type = "2";
        }

        public OccupancyCreator build() {
            return new OccupancyCreator(this);
        }

    }

    public Occupancy createRentalUnit() {
        switch (type) {
            case "1":
                return new Condo(unitNumber,
                        bedroomCount,
                        bathroomCount,
                        squareFootage, address);
            case "2":
                return new House(address);
            default:
                return new Apartment(apartmentNumber, bedroomCount,
                        bathroomCount, squareFootage, address);
        }
    }

}
