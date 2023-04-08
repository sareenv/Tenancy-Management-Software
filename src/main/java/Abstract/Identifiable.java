package Abstract;

import java.util.UUID;

public abstract class Identifiable {
    public final UUID id = UUID.randomUUID();
}