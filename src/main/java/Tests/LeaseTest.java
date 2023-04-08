package Tests;

import Builder.OccupancyCreator;
import DataAccess.Service;
import Model.*;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LeaseTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private String leasePath;
    private Service<Tenant> tenantService;
    private File file;
    String filePath;

    @Before
    public void before() throws IOException {
        file = new File("");
        filePath = file.getAbsoluteFile().toString()
                + "/src/main/java/Tests/temp.txt";
        file = new File(filePath);
        file.createNewFile();
        System.setOut(new PrintStream(out));
        leasePath = filePath;
        tenantService = new Service<Tenant>(leasePath);
    }

    @After
    public void after() {
        leasePath = "";
        System.setOut(originalOut);
        file.delete();
        tenantService = null;
    }


    @Test
    public void checkInvalidLeaseDate() throws ParseException {
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2020");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2020");
        Lease lease = new Lease(null, null, startDate, endDate);
        Assert.assertFalse(lease.checkLeaseValidity());
    }

    @Test
    public void checkNullLeaseObjects() throws ParseException {
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2020");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2020");
        Lease lease = new Lease(null, null, startDate, endDate);
//        Assert.assertThrows(Exception.class, lease::registerLease);
    }

    @Test
    public void checkOccupiedOccupancyLease() throws Exception {
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        Occupancy occupancy = creator.createRentalUnit();

        // Given: The occupancy is not available.
        occupancy.setIsOccupancyAvailable(false);

        // When: The users tries to register a lease.
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/09/2024");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2025");
        Tenant tenant = new Tenant("Suraj", "Student",
                2100.0, tenantService);


        Lease lease = new Lease(occupancy, tenant, startDate, endDate);
        try {
            lease.registerLease();
        } catch (Exception e) {
            // Then: The observer must have been added to the administrator.
            Administrator administrator = Administrator.makeSharedInstance();
            boolean result = administrator.waitListedProperty.get(occupancy).contains(tenant);
            Assert.assertTrue(result);
        }
    }

    @Test
    public void checkWaistedListedObserver() throws Exception {
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        Occupancy occupancy = creator.createRentalUnit();

        // Given: The occupancy is not available.
        occupancy.setIsOccupancyAvailable(false);

        // When: The users tries to register a lease.
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/09/2024");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2025");
        Tenant tenant = new Tenant("Suraj", "Student",
                2100.0, tenantService);


        Lease lease = new Lease(occupancy, tenant, startDate, endDate);
        try {
            lease.registerLease();
        } catch (Exception e) {
            // Then: The observer must have been added to the administrator.
            occupancy.setIsOccupancyAvailable(true);
            Administrator administrator = Administrator.makeSharedInstance();
            ArrayList<Tenant> waitListedTenants = administrator.waitListedProperty.get(occupancy);
            administrator.notifyTenants(waitListedTenants, occupancy);
            String houseId = occupancy.getId().toString();
            // Confirms that observer is working as expected.
            Assert.assertTrue(out.toString().contains(houseId));
        }
    }

    @Test
    public void saveInvalidLease() throws ParseException {
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2020");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2020");
        Lease lease = new Lease(null, null, startDate, endDate);
//        Assert.assertThrows(Exception.class, lease::registerLease);
    }

    @Test
    public void getAllExpiredLease() throws ParseException {
        ArrayList<Lease> allLease = Lease.getAllLease(filePath);
        Assert.assertEquals(allLease.size(), 0);
        Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2020");
        Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse("04/01/2020");
        Lease testLease = new Lease(null, null, startDate, endDate);
        allLease.add(testLease);
        ArrayList<Lease> expiredLease = Lease.getExpiredLease(allLease);
        int expectedSize = 1;
        Assert.assertEquals(expectedSize, expiredLease.size());
    }
}
