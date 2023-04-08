package Model;

import Abstract.Observer;
import Abstract.Subject;
import DataAccess.Service;
import Interface.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


enum Type { WaitList, Lease, Expired, New }

public class Tenant extends Observer implements Serializable {
    UUID id;
    String name;
    String occupation;
    Double monthlyIncome;
    Type tenantType;
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

    @Override
    public void update(Subject subject, Object event) {
        System.out.println("The Wait listed occupancy is now Available with the details below");
        System.out.println(event);
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
        return Objects.equals(id, tenant.id) && Objects.equals(name, tenant.name) && Objects.equals(occupation, tenant.occupation) && Objects.equals(monthlyIncome, tenant.monthlyIncome) && getTenantType() == tenant.getTenantType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, occupation, monthlyIncome, getTenantType());
    }
}
