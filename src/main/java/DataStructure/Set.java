package DataStructure;

import java.util.ArrayList;
import java.util.Objects;

// set class extends identifiableObject Interface
public class Set<T extends IdentifiableObject> {

    // ArrayList for internal implementation for SET class
    ArrayList<T> lst = new ArrayList<>();

    // Add an element to SET
    public void addElement(T element) {
        if (!lst.contains(element)) {
            lst.add(element);
        }
    }

    // remove an element from set
    public T removeElement(Integer id) {
        T result = null;
        synchronized (this) {
            for (T obj: lst) {
                Integer objectID = obj.getObjectID();
                if (objectID.equals(id)) {
                    result = obj;
                    lst.remove(obj);
                    return result;
                }
            }
        }
        return null;
    }

    // gets an element from set on basis of ID
    public Boolean peek(Integer id) {
        for (T obj: lst) {
            Integer objectID = obj.getObjectID();
            if (objectID.equals(id)) { return true; }
        }
        return false;
    }

    // gets the size of the size
    public Integer size() { return lst.size(); }

    // display elements in stack
    public void displayElements() {
        if (lst.isEmpty()) {
            System.out.println("No elements are found");
            return;
        }
        for (T object: lst) {
            System.out.println(object);
        }
    }

    // overiding the equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;
        Set<?> set = (Set<?>) o;
        return lst.equals(set.lst);
    }

    // returns the hash code
    @Override
    public int hashCode() {
        return Objects.hash(lst);
    }
}
