package Model;

import Abstract.Observer;
import Interface.AdminOperations;
import Abstract.Subject;
import Interface.Constants;
import Interface.ConstantsService;

import java.io.Serializable;
import java.util.*;


// Use of the Singleton pattern because there will also be one instance of the Administrator.
public class Administrator extends Subject implements AdminOperations, Serializable {

    static Administrator shared = null;
    private Administrator() {}
    public HashMap<Occupancy, ArrayList<Tenant>> waitListedProperty =  new HashMap<>();

    public static Administrator makeSharedInstance() {
        if (shared == null) {
            shared = new Administrator();
        }
        return shared;
    }

    private ArrayList<Tenant> getWaitListedTenants() {
        return Tenant.getWaitlistedTenants();
    }

    @Override
    public Lease registerLease(Tenant tenant,
                               Occupancy occupancy,
                               Date tenancyStartDate,
                               Date tenancyEndDate) {

        Lease lease = new Lease(occupancy, tenant,
                tenancyStartDate, tenancyEndDate);
        try {
            lease.registerLease();
        } catch (Exception e) {
            lease =  null;
        }
        return lease;
    }

    @Override
    public ArrayList<Occupancy> availableOccupancyUnit(Date currentDate) {
        return Occupancy.findOccupanciesWithStatus(Constants.OccupancyPath, true);
    }

    @Override
    public boolean addTenant(Tenant tenant) {
        return tenant.addTenant();
    }

    public boolean addTenant(String name, String occupation,
                             Double monthlyIncome) {
        Tenant tenant = new Tenant(name, occupation, monthlyIncome,
                ConstantsService.tenantService);
        return addTenant(tenant);
    }

    @Override
    public void addUnit(Occupancy occupancy) throws Exception  {
        Occupancy.addUnit(occupancy, Constants.OccupancyPath);
        notifyAllObserver();
    }


    @Override
    public void attachObserver(Observer observer, Object event) {
        Occupancy o = (Occupancy) event;
        Tenant tenant = (Tenant) observer;
        if (!waitListedProperty.containsKey(o)) {
            waitListedProperty.put(o, new ArrayList<>());
        }
        ArrayList<Tenant> waitListedTenants = waitListedProperty.get(o);
        if (!waitListedTenants.contains(tenant)) {
            waitListedTenants.add(tenant);
        }
        waitListedProperty.put(o, waitListedTenants);
    }

    @Override
    public void notifyAllObserver() {
        ArrayList<Occupancy> availableOccupancies = Occupancy
                .findOccupanciesWithStatus(Constants.OccupancyPath, true);
        for(Occupancy o: availableOccupancies) {
            if (o.isOccupancyAvailable && waitListedProperty.containsKey(o)) {
                ArrayList<Tenant> waitListedTenants = waitListedProperty.get(o);
                notifyTenants(waitListedTenants, o);
            }
        }
    }

    public void notifyTenants(ArrayList<Tenant> tenants, Object o) {
        for (Tenant tenant: tenants) {
            tenant.update(this, o);
        }
    }

    @Override
    public void detachObserver(Observer observer) {
        Tenant removeTenant = (Tenant) observer;
        for (Map.Entry<Occupancy, ArrayList<Tenant>> entry: waitListedProperty.entrySet()) {
            ArrayList<Tenant> tenants = entry.getValue();
            tenants.remove(removeTenant);
            waitListedProperty.put(entry.getKey(), tenants);
        }
    }
}
