package Model;

import DataAccess.Service;
import Interface.Constants;
import Interface.ConstantsService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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

    public Occupancy getOccupancy() {
        return occupancy;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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

    public boolean deleteLease()  {
        ArrayList<Lease> allLease  = getAllLease(Constants.LeasePath);
        if (allLease.contains(this)) {
            Service<Lease> leaseService = new Service<Lease>(Constants.LeasePath);
            Service<Occupancy> occupancyService = new Service<>(Constants.OccupancyPath);
            try {
                Occupancy updateOccupancy = this.occupancy.deepClone();
                updateOccupancy.setIsOccupancyAvailable(true);
                occupancyService.updateRecord(this.occupancy, updateOccupancy);
                Administrator.makeSharedInstance().notifyAllObserver();
                return leaseService.deleteRecord(this);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Lease" +
                " Occupancy: " + occupancy + "\n" +
                " Tenant: " + tenant + "\n" +
                " StartDate: " + startDate + "\n" +
                " EndDate=" + endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lease)) return false;
        Lease lease = (Lease) o;
        return getOccupancy().equals(lease.getOccupancy()) && getTenant().equals(lease.getTenant()) && getStartDate().equals(lease.getStartDate()) && getEndDate().equals(lease.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOccupancy(), getTenant(), getStartDate(), getEndDate());
    }
}
