package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Notification {
    String tenantID;
    String tenantName;
    String occupancyId;
    String address;
    String message;

    public Notification(String tenantID, String tenantName, String occupancyId, String address) {
        this.tenantID = tenantID;
        this.tenantName = tenantName;
        this.occupancyId = occupancyId;
        this.address = address;
        setupMessage();
    }

    private void setupMessage() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        message = "Property is available made available for lease at " + formattedDate + "we don't guarantee how " +
                "long it will be available further";
    }

    @Override
    public String toString() {
        return "Notification{" +
                "tenantID='" + tenantID + '\'' +
                ", tenantName='" + tenantName + '\'' +
                ", occupancyId='" + occupancyId + '\'' +
                ", address='" + address + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return getTenantID().equals(that.getTenantID()) && getTenantName().equals(that.getTenantName()) && getOccupancyId().equals(that.getOccupancyId()) && getAddress().equals(that.getAddress()) && getMessage().equals(that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTenantID(), getTenantName(), getOccupancyId(), getAddress(), getMessage());
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOccupancyId() {
        return occupancyId;
    }

    public void setOccupancyId(String occupancyId) {
        this.occupancyId = occupancyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
