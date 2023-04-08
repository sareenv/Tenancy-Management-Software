package Tests;

import DataAccess.Service;
import Tests.Objects.MockObject;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ServiceTest {

    private static Service<MockObject> service;
    private static File file;

    @Before
    public void beforeAll() throws IOException {
        file = new File("");
        String filePath = file.getAbsoluteFile().toString()
                + "/src/main/java/Tests/temp.txt";
        file = new File(filePath);
        file.createNewFile();
        service = new Service<MockObject>(filePath);
    }

    @After
    public void afterAll() {
        file.delete();
        service = null;
    }

    @Test
    public void saveRecordList() {
        // Given: The new object of the type MockObject that extends Serializable.
        MockObject object = new MockObject("test");

        // When: The user saves the record.
        service.saveRecord(object);
        MockObject result  = service.findAllRecordsFromFile().get(0);

        // Then: Record must be saved and have the same name property.
        Assert.assertNotNull(result);
        String expectedName = "test";
        Assert.assertNotNull(result);
        Assert.assertEquals(expectedName, result.getName());
    }

    @Test
    public void fetchEmptyRecords() {

        // When: The User finds all the records that were never saved to file.
        ArrayList<MockObject> lst = service.findAllRecordsFromFile();
        int expectedRecordListSize = 0;

        // Then: The records must return the empty array of MockObjects.
        Assert.assertNotNull(lst);
        Assert.assertEquals(expectedRecordListSize, lst.size());
    }

    @Test
    public void fetchMultipleExistingRecords() {

        // Given: The user has already saved the mocked objects as records.
        MockObject object = new MockObject("test");
        MockObject object2 = new MockObject("test2");
        int expectedRecordListSize = 2;
        service.saveRecord(object);
        service.saveRecord(object2);

        // When: The user finds all the saved records.
        ArrayList<MockObject> lst = service.findAllRecordsFromFile();

        // Then: The saved results must be contained in the list with the same size and list must not be null.
        Assert.assertNotNull(lst);
        Assert.assertEquals(expectedRecordListSize, lst.size());
    }

    @Test
    public void deleteNonExistingRecord() {
        // Given: the object which has never been stored as the record to the File.
        MockObject object = new MockObject("test");

        // When: The user tries to delete the unsaved record.
        boolean deleted = service.deleteRecord(object);

        // Then: The system will send the false deletion flag and will not delete it.
        Assert.assertFalse(deleted);
    }

    @Test
    public void deleteExistingRecord() {
        // Given: the objects which has been stored as the record to the File.
        MockObject object = new MockObject("test");
        MockObject object1 = new MockObject("test2");
        service.saveRecord(object);
        service.saveRecord(object1);

        // When: The user tries to delete the unsaved record.
        boolean deleted = service.deleteRecord(object);

        // Then:
        // 1. The system will send the false deletion flag and will delete requested record.
        // 2. Contain the non deleted records in the system.
        int expectedSize = 1;
        ArrayList<MockObject> records = service.findAllRecordsFromFile();
        Assert.assertTrue(deleted);
        Assert.assertEquals(expectedSize, records.size());
        Assert.assertTrue(records.contains(object1));
    }

    @Test
    public void updateNonExistingRecord() {
        // Given: the object which has never been stored as the record to the File.
        MockObject object = new MockObject("test");

        // When: The user tries to update the new record stating it as a stored record.
        MockObject updatedRecord = service.updateRecord(object, object);

        // Then: The system should send the updated flag as false.
        Assert.assertNull(updatedRecord);
    }


    @Test
    public void updateExistingRecord() {
        // Given: the object which has been stored as the record to the File.
        MockObject object = new MockObject("test");
        service.saveRecord(object);
        MockObject changedObject = new MockObject("test2");


        // When: The user tries to update the new record details with a stored record.
        MockObject updatedRecord = service.updateRecord(object, changedObject);

        // Then: The system should send the updated flag as true and update the details of old record.
        ArrayList<MockObject> records = service.findAllRecordsFromFile();
        Assert.assertNotNull(updatedRecord);
        Assert.assertTrue(records.contains(updatedRecord));
    }

}