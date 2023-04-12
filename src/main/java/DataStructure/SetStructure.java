package DataStructure;

// SetStructure used as holder inside the SET class
public class SetStructure implements  IdentifiableObject{

    // element have Object Id
    private Integer objectId = null;

    // element have value
    private String element = null;

    // constructor
    public SetStructure(int objectId, String element){
        this.objectId = objectId;
        this.element = element;
    }

    // overriding toString
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
