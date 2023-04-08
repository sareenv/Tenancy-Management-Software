package Tests;

import DataStructure.Set;
import Tests.Objects.IdentityObject;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SetTest {
    private IdentityObject object;
    private Set<IdentityObject> set;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void before() {
        object = new IdentityObject();
        set = new Set<IdentityObject>();
        System.setOut(new PrintStream(out));

    }

    @After
    public void after() {
        object = null;
        set = null;
        System.setOut(originalOut);
    }

    @Test
    public void addDuplicatedElement() {
        // When the element is added to the set.
        set.addElement(object);
        set.addElement(object);
        // The expected size must be one considering we ignore duplicate entries.
        int actualSize = set.size();
        int expectedSize = 1;
        Assert.assertEquals(expectedSize, actualSize);
        Assert.assertTrue(set.peek(object.getObjectID()));
    }

    @Test
    public void testNonExistingElementRemoval() {
        Integer objectID = object.getObjectID();
        IdentityObject result = set.removeElement(objectID);
        Assert.assertNull(result);
    }

    @Test
    public void testExistingElementRemoval() {
        Integer objectID = object.getObjectID();
        set.addElement(object);
        IdentityObject result = set.removeElement(objectID);
        Assert.assertNotNull(result);
        Assert.assertEquals(objectID, result.getObjectID());
    }

    @Test
    public void testSetSize() {
        IdentityObject object = new IdentityObject();
        IdentityObject object1 = new IdentityObject();
        IdentityObject object2 = new IdentityObject();
        Integer expectedSize = 3;
        // redundant entries - counted once only.
        for (int i = 0; i< 10; i++) { set.addElement(object); }
        // normal entries.
        set.addElement(object2);
        set.addElement(object1);
        // The expected count must ignore the all the redundant entries except the original one.
        Assert.assertEquals(expectedSize, set.size());
    }

    @Test
    public void testPrintStatement() {
        set.addElement(object);
        int objectID = object.getObjectID();
        // When the elements are displayed.
        set.displayElements();
        // The output must contain the record's id which has been saved
        String objectIDString = "" + objectID;
        boolean result = out.toString().contains(objectIDString);
        Assert.assertTrue(result);
    }

}
