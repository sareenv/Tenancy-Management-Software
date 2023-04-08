package Tests;

import DataAccess.Service;
import Builder.OccupancyCreator;
import Model.*;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OccupancyTest {

    Service<Occupancy> occupancyService;
    Service<Lease> leaseService;
    Service<Tenant> tenantService;

    String occupanciesPath;
    private static File file;

    private void createFile(String filePath) throws IOException {
        file = new File(filePath);
        file.createNewFile();
    }

    @Before
    public void before() throws IOException {
        file = new File("");
        occupanciesPath = file.getAbsoluteFile().toString()
                + "/src/main/java/Tests/temp.txt";

        String leasePath = file.getAbsoluteFile().toString()
                + "/src/main/java/Tests/temp.txt";;

        String tenantsPath = file.getAbsoluteFile().toString()
                + "/src/main/java/Tests/temp.txt";;

        createFile(occupanciesPath);
        createFile(leasePath);
        occupancyService = new Service<Occupancy>(occupanciesPath);
        leaseService = new Service<Lease>(leasePath);
        tenantService = new Service<Tenant>(tenantsPath);
    }

    @After
    public void after() {
        file.delete();
        occupancyService = null;
        leaseService = null;
    }

    @Test
    public void createRentalUnit() {
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        Occupancy occupancy = creator.createRentalUnit();
        Assert.assertNotNull(occupancy);
    }

    @Test
    public void addRentalUnit() {
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        Occupancy occupancy = creator.createRentalUnit();
        occupancyService.saveRecord(occupancy);
        ArrayList<Occupancy> occupencies = occupancyService.findAllRecordsFromFile();
        int expectedSize = 1;
        Occupancy fetchOccupancy = occupencies.get(0);
        boolean isAvailableOccupancy = fetchOccupancy.isOccupancyAvailable();
        Assert.assertEquals(expectedSize, occupencies.size());
        Assert.assertTrue(isAvailableOccupancy);
        Assert.assertEquals(occupancy.getOccupancyAddress(), houseAddress);
    }

    @Test
    public void testOccupancyAvailability() {
        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");
        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        House occupancy = (House) creator.createRentalUnit();
        Assert.assertTrue(occupancy.isOccupancyAvailable());
        occupancy.setIsOccupancyAvailable(false);
        Assert.assertFalse(occupancy.isOccupancyAvailable());
    }

    @Test
    public void findAvailableOccupancies() {

        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");

        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        House occupancy = (House) creator.createRentalUnit();

        occupancyService.saveRecord(occupancy);

        ArrayList<Occupancy> occupancies =  Occupancy.findOccupanciesWithStatus(occupanciesPath, true);
        for (Occupancy o: occupancies) {
            if (!o.isOccupancyAvailable()) {
                Assert.fail("Property is not available ");
            }
        }

        int expectedCount = 1;
        Assert.assertEquals(expectedCount, occupancies.size());
    }

    @Test
    public void findUnAvailableOccupancies() {

        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");

        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        House occupancy = (House) creator.createRentalUnit();

        occupancyService.saveRecord(occupancy);

        ArrayList<Occupancy> occupancies =  Occupancy.findOccupanciesWithStatus(occupanciesPath, false);
        for (Occupancy o: occupancies) {
            if (!o.isOccupancyAvailable()) {
                Assert.fail("Property is not available ");
            }
        }

        int expectedCount = 0;
        Assert.assertEquals(expectedCount, occupancies.size());
    }

    @Test
    public void findUnAllOccupancies() {

        Address houseAddress = new Address("2175", "De Maisonnuve Ouest",
                "H3H 1L5", "Montreal", "Canada");

        OccupancyCreator creator = new OccupancyCreator.Builder(houseAddress).build();
        House occupancy = (House) creator.createRentalUnit();
        House occupancy2 = (House) creator.createRentalUnit();

        occupancyService.saveRecord(occupancy);
        occupancyService.saveRecord(occupancy2);

        ArrayList<Occupancy> occupancies =  Occupancy.findOccupanciesWithStatus(occupanciesPath, null);
        for (Occupancy o: occupancies) {
            if (!o.isOccupancyAvailable()) {
                Assert.fail("Property is not available ");
            }
        }

        int expectedCount = 2;
        Assert.assertEquals(expectedCount, occupancies.size());
    }

}
