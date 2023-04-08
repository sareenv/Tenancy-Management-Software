package Interface;

import java.io.File;

public interface Constants {
    public static final String absolutePath = new File("").getAbsoluteFile().toString();
    public static final String OccupancyPath = absolutePath + "/src/Data/Occupancy.koi";
    public static final String LeasePath = absolutePath + "/src/Data/Lease.koi";
    public static final String TentPath = absolutePath + "/src/Data/Tenant.koi";
}
