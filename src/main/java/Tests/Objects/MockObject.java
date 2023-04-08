package Tests.Objects;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class MockObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String name;

    public MockObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public UUID getId() { return UUID.fromString(String.valueOf(serialVersionUID)); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MockObject)) return false;
        MockObject object = (MockObject) o;
        return Objects.equals(getName(), object.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
