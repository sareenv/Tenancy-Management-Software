package Model;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    String streetNumber;
    String streetName;
    String postalCode;
    String city;
    String country;

    public Address(String streetNumber, String streetName,
                   String postalCode,
                   String city,
                   String country) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return streetNumber.equals(address.streetNumber) && streetName.equals(address.streetName) && postalCode.equals(address.postalCode) && city.equals(address.city) && country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetNumber, streetName, postalCode, city, country);
    }

    @Override
    public String toString() {
        return "Address " + "\n" +
                " streetNumber: " + streetNumber + "\n" +
                " streetName: " + streetName + "\n" +
                " postalCode: " + postalCode + "\n" +
                " city: " + city + "\n" +
                " country: " + country;
    }
}