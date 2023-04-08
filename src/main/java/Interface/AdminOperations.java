package Interface;

import Model.Lease;
import Model.Occupancy;
import Model.Tenant;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface AdminOperations {
    public boolean addTenant(Tenant tenant);
    public void addUnit(Occupancy occupancy) throws Exception;
    public Lease registerLease(Tenant tenant, Occupancy occupancy,
                               Date tenancyStartDate, Date tenancyEndDate);
    public ArrayList<Occupancy> availableOccupancyUnit(Date currentDate);
}
