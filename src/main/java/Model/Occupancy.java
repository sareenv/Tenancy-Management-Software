package Model;

import DataAccess.Service;
import Interface.ConstantsService;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public abstract class Occupancy implements Serializable {
    public final UUID id = UUID.randomUUID();
    public UUID getId() { return id; }
    boolean isOccupancyAvailable = true;
    public abstract Address getOccupancyAddress();
    public void setIsOccupancyAvailable(boolean isAvailable) {this.isOccupancyAvailable = isAvailable; }
    public boolean isOccupancyAvailable() { return isOccupancyAvailable; }
    public static void addUnit(Occupancy occupancy, String filePath) {
        Service<Occupancy> occupancyService = new Service<Occupancy>(filePath);
        occupancyService.saveRecord(occupancy);
    }
    public static  ArrayList<Occupancy> findOccupanciesWithStatus(String filePath, Boolean isAvailable) {
        Service<Occupancy> occupancyService = new Service<Occupancy>(filePath);
        ArrayList<Occupancy> availableOccupancies = new ArrayList<>();
        ArrayList<Occupancy> allOccupancies = occupancyService.findAllRecordsFromFile();
        if (isAvailable == null) { return allOccupancies; }
        for (Occupancy occupancy: allOccupancies) {
            if (occupancy.isOccupancyAvailable == isAvailable) {
                availableOccupancies.add(occupancy);
            }
        }
        return availableOccupancies;
    }

    public static  Occupancy findOccupancyByID(String id, String filePath) {
        Service<Occupancy> occupancyService = ConstantsService.occupancyService;
        ArrayList<Occupancy> allOccupancies = occupancyService.findAllRecordsFromFile();
        for (Occupancy occupancy: allOccupancies) {
            if(occupancy.getId().toString().equals(id)) {
                return occupancy;
            }
        }
        return null;
    }

    public Occupancy deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
        bos.close();
        byte[] byteData = bos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
        return (Occupancy) new ObjectInputStream(bais).readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Occupancy)) return false;
        Occupancy occupancy = (Occupancy) o;
        return getId().equals(occupancy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}