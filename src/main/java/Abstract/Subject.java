package Abstract;

import Abstract.Observer;
import java.util.ArrayList;

public abstract class Subject {

    abstract public void attachObserver(Observer observer, Object event);

    abstract public void notifyAllObserver();

    abstract public void detachObserver(Observer observer);
}