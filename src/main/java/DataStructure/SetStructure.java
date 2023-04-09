package DataStructure;

public class SetStructure implements  IdentifiableObject{

    private Integer objectID = null;
    private String element = null;

    public SetStructure(int objectId, String element){
        this.objectID = objectId;
        this.element = element;
    }

    @Override
    public String toString() {
        return "Set{" +
                "objectId=" + objectID +
                ", element='" + element + '\'' +
                '}';
    }

    @Override
    public Integer getObjectID() {
        return objectID;
    }
}
