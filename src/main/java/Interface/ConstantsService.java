package Interface;

import DataAccess.Service;
import Model.Lease;
import Model.Occupancy;
import Model.Tenant;

public interface ConstantsService {
    public static final Service<Tenant> tenantService = new Service<Tenant>(Constants.TentPath);
    public static final Service<Lease> leaseService = new Service<Lease>(Constants.LeasePath);
    public static final Service<Occupancy> occupancyService = new Service<Occupancy>(Constants.OccupancyPath);
}
