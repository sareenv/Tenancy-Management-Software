package Tests.Objects;

import DataStructure.IdentifiableObject;
import java.util.Random;

public class IdentityObject implements IdentifiableObject {
    private final Integer objectID;

    public IdentityObject() {
        Random random = new Random();
        objectID = random.nextInt();
    }

    @Override
    public Integer getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return "IdentityObject{" +
                "objectID=" + objectID +
                '}';
    }
}
