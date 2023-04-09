package DataStructure;

public class SetStructure implements  IdentifiableObject{

    private Integer objectId = null;
    private String element = null;

    public SetStructure(int objectId, String element){
        this.objectId = objectId;
        this.element = element;
    }

    @Override
    public String toString() {
        return "SetStructure{" +
                "objectId=" + objectId +
                ", element='" + element + '\'' +
                '}';
    }

    @Override
    public Integer getObjectID() {
        return null;
    }
}
