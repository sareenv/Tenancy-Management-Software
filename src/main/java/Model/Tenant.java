package Model;

import Abstract.Observer;
import Abstract.Subject;
import DataAccess.Service;
import Interface.Constants;
import com.example.fxproject.NotificationController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


enum Type { WaitList, Lease, Expired, New }

public class Tenant extends Observer implements Serializable {
    public UUID id;
    public String name;
    public String occupation;
    public Double monthlyIncome;
    public Type tenantType;
    static Service<Tenant> tenantService;

    public Tenant(String name, String occupation,
                  Double monthlyIncome,
                  Service<Tenant> tenantService) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.occupation = occupation;
        this.monthlyIncome = monthlyIncome;
        this.tenantType = Type.New;
        Tenant.tenantService = tenantService;
    }

    public String getTenantType() {
        switch (tenantType) {
            case WaitList:
                return "WaitList";
            case Lease:
                return "Lease";
            case New:
                return "New";
            default:
                return "Expired";
        }
    }

    // TODO: - Need to look into this one.
    @Override
    public void update(Subject subject, Object event) {
        if (event instanceof Occupancy) {
            Occupancy occupancy = (Occupancy) event;
            Notification notification = new Notification(this.id.toString(),
                    this.name,
                    occupancy.id.toString(),
                    occupancy.getOccupancyAddress().toString());
            System.out.println("Getting the notification as " + notification);
            NotificationController.updatedNotification(notification);
        } else {
            System.out.println("Some other notification");
        }
    }

    boolean addTenant() {
        Tenant tenant = tenantService.saveRecord(this);
        return (tenant != null);
    }

    public static ArrayList<Tenant> getAllTenants() {
        Service<Tenant> tenantService = new Service<Tenant>(Constants.TentPath);
        return tenantService.findAllRecordsFromFile();
    }

    public static Tenant findTenantByID(String tenantID) {
        ArrayList<Tenant> allTenants = Tenant.getAllTenants();
        for (Tenant tenant: allTenants) {
            if (tenant.id.toString().equals(tenantID)) {
                return tenant;
            }
        }
        return null;
    }

    static ArrayList<Tenant> getWaitlistedTenants() {

        ArrayList<Tenant> waitList = new ArrayList<>();
        ArrayList<Tenant> allTenants = getAllTenants();
        for (Tenant tenant: allTenants) {
            if (tenant.tenantType == Type.WaitList) {
                waitList.add(tenant);
            }
        }
        return waitList;
    }

    @Override
    public String toString() {
        return "Tenant: " + "\n" +
                "   id: " + id + "\n" +
                "   name: " + name + "\n" +
                "   occupation: " + occupation + "\n" +
                "   monthlyIncome: " + monthlyIncome + "\n" +
                "   tenantType: " + tenantType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tenant)) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(id, tenant.id) && Objects.equals(name, tenant.name) &&
                Objects.equals(occupation, tenant.occupation)
                && Objects.equals(monthlyIncome, tenant.monthlyIncome)
                && getTenantType().equals(tenant.getTenantType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation, monthlyIncome, getTenantType());
    }
}
