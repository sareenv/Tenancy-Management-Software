package DataStructure;

import java.util.ArrayList;
import java.util.Objects;


public class Set<T extends IdentifiableObject> {

    ArrayList<T> lst = new ArrayList<>();

    public void addElement(T element) {
        if (!lst.contains(element)) {
            lst.add(element);
        }
    }

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

    public Boolean peek(Integer id) {
        for (T obj: lst) {
            Integer objectID = obj.getObjectID();
            if (objectID.equals(id)) { return true; }
        }
        return false;
    }
    public Integer size() { return lst.size(); }

    public void displayElements() {
        for (T object: lst) {
            System.out.println(object);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;
        Set<?> set = (Set<?>) o;
        return lst.equals(set.lst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lst);
    }
}
