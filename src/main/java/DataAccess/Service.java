package DataAccess;

import java.io.*;
import java.util.ArrayList;

public class Service<T extends Serializable> {

    private OutputStream fileOutputStream;
    private InputStream fileInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String filePath;

    public Service(String filePath)  {
        this.setupFileStreams();
        this.filePath = filePath;
    }

    private void setupFileStreams()  {
        try {
            fileInputStream = new FileInputStream(filePath);
            fileOutputStream = new FileOutputStream(filePath);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        } catch (Exception exception) { }
    }

    private ArrayList<T> writeEmptyList() {
        ArrayList<T> lst = new ArrayList<T>();
        try {
            objectOutputStream.writeObject(lst);
        } catch (IOException e) {
            e.printStackTrace();
            lst = null;
        }
        return lst;
    }

    public T saveRecord(T record) {
        try {
            ArrayList<T> recordList  = findAllRecordsFromFile();
            recordList.add(record);
            writeRecordToFile(recordList);
            return record;
        }catch (Exception exception) {
            return null;
        }
    }

    void writeRecordToFile(ArrayList<T> arrayList) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(arrayList);
            out.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<T> findAllRecordsFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<T> recordList = (ArrayList<T>) in.readObject();
            in.close();
            fileIn.close();
            return recordList;
        } catch (IOException | ClassNotFoundException e) {
            // If nothing has been recording yet to the File.
            return new ArrayList<>();
        }
    }

    public boolean deleteRecord(T record) {
        ArrayList<T> records = findAllRecordsFromFile();
        boolean result = records.remove(record);
        if (result) {
            writeRecordToFile(records);
            return true;
        }
        return false;
    }

    public T updateRecord(T existingRecord, T newRecord) {
        ArrayList<T> records = findAllRecordsFromFile();
        if (records.contains(existingRecord)) {
            deleteRecord(existingRecord);
            saveRecord(newRecord);
            return newRecord;
        }
        return null;
    }
}