package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class HeapConstructor {
    private ArrayList<String> paths;
    private HashMap<Integer, ObjectInfo> heap;

    public HeapConstructor(ArrayList<String> paths) throws Exception {
        if(paths.size() != 4){
            throw new Exception("Insufficient Arguments");
        }
        this.paths = paths;
        this.heap = new HashMap<>();
    }

    public HashMap<Integer, ObjectInfo> getHeap() throws IOException {
        try {
            File file = new File(paths.get(0));
            FileReader fr = new FileReader(file);
            BufferedReader parser = new BufferedReader(fr);
            String lineHolder;
            String[] bucketsHolder;

            try {
                while ((lineHolder = parser.readLine()) != null) {
                    bucketsHolder = lineHolder.split(",");
                    ObjectInfo objectInfo = new ObjectInfo(Integer.parseInt(bucketsHolder[0]), Integer.parseInt(bucketsHolder[1]) ,Integer.parseInt(bucketsHolder[2]));
                    this.heap.put(objectInfo.getId(), objectInfo);
                }
            }catch (Exception readLineException) {
                System.out.println("ReadLine Exception");
                System.exit(1);
            }
            parser.close();
        }catch (Exception e){
            System.out.println("IO Exception");
            System.exit(1);
        }

        this.readPointers();
        return this.heap;
    }

    public ArrayList<Integer> getRoots() throws IOException {
        ArrayList<Integer> roots = new ArrayList<>();
        try {
            File file = new File(paths.get(1));
            FileReader fr = new FileReader(file);
            BufferedReader parser = new BufferedReader(fr);
            String lineHolder;

            try {
                while ((lineHolder = parser.readLine()) != null) {
                    roots.add(Integer.parseInt(lineHolder));
                }
            }catch (Exception readLineException) {
                System.out.println("ReadLine Exception");
                System.exit(1);
            }
            parser.close();
        }catch (Exception e){
            System.out.println("IO Exception");
            System.exit(1);
        }

        return roots;
    }

    public void readPointers() throws IOException {

        try {
            File file = new File(paths.get(2));
            FileReader fr = new FileReader(file);
            BufferedReader parser = new BufferedReader(fr);
            String lineHolder;
            String[] bucketsHolder;

            try {
                while ((lineHolder = parser.readLine()) != null) {
                    bucketsHolder = lineHolder.split(",");
                    this.heap.get(Integer.parseInt(bucketsHolder[0])).addChild(heap.get(Integer.parseInt(bucketsHolder[0])));
                }
            }catch (Exception readLineException) {
                System.out.println("ReadLine Exception");
                System.exit(1);
            }
            parser.close();
        }catch (Exception e){
            System.out.println("IO Exception");
            System.exit(1);
        }
    }

    public FileWriter getDestinationFile() throws IOException {
        FileWriter destinationFile = null;
        try {
            destinationFile =  new FileWriter(paths.get(3));
        }catch (Exception e){
            System.out.println("IO Exception");
            System.exit(1);
        }
        return destinationFile;
    }
}
