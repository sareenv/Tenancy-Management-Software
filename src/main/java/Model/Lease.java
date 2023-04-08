package Model;

import DataAccess.Service;
import Interface.ConstantsService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Lease implements Serializable {
    Occupancy occupancy;
    Tenant tenant;
    Date startDate;
    Date endDate;

    public Lease(Occupancy occupancy, Tenant tenant, Date startDate, Date endDate) {
        this.occupancy = occupancy;
        this.tenant = tenant;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean checkLeaseValidity() {
        return (startDate.compareTo(endDate) < 0 && startDate.compareTo(new Date()) >=0);
    }

    public void registerLease() throws Exception {
        if (occupancy == null || tenant == null) { throw new Exception("Occupancy or tenant is null"); }
        if(!occupancy.isOccupancyAvailable) {
            Administrator admin = Administrator.makeSharedInstance();
            admin.attachObserver(tenant, occupancy);
            throw new Exception("Occupancy has already been in with lease you will be notified when become available");
        }
        if (checkLeaseValidity()) {
            Service<Occupancy> occupancyService = ConstantsService.occupancyService;
            occupancyService.deleteRecord(occupancy);
            occupancy.setIsOccupancyAvailable(false);
            occupancyService.saveRecord(occupancy);
            Service<Lease> leaseService = ConstantsService.leaseService;
            leaseService.saveRecord(this);
        } else { throw new Exception("Error registering lease"); }
    }

    public static ArrayList<Lease> getExpiredLease(ArrayList<Lease> allLease) {
        ArrayList<Lease> result = new ArrayList<>();
        for (Lease lease: allLease) {
            if (!lease.checkLeaseValidity()) {
                result.add(lease);
            }
        }
        return result;
    }

    public static ArrayList<Lease> getAllLease(String servicePath) {
        Service<Lease> leaseService = new Service<Lease>(servicePath);
        return leaseService.findAllRecordsFromFile();
    }

    @Override
    public String toString() {
        return "Lease" +
                " Occupancy: " + occupancy + "\n" +
                " Tenant: " + tenant + "\n" +
                " StartDate: " + startDate + "\n" +
                " EndDate=" + endDate;
    }
}
