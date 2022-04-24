package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HeapConstructor {
    private final String[] paths;
    private HashMap<Integer, ObjectInfo> heap;

    public HeapConstructor(String[] paths) throws Exception {
        if (paths.length != 4) {
            System.out.println("paths length " + Arrays.toString(paths));
            throw new Exception("Insufficient Arguments");
        }
        this.paths = paths;
        this.heap = new HashMap<>();
    }

    /**
     * @return heap hashMap, each entry is an identifier to an object and its value is the object itself
     */
    public HashMap<Integer, ObjectInfo> getHeap() {
        try {
            File file = new File(paths[0]);
            FileReader fr = new FileReader(file);
            BufferedReader parser = new BufferedReader(fr);
            String lineHolder;
            String[] bucketsHolder;

            try {
                while ((lineHolder = parser.readLine()) != null) {
                    bucketsHolder = lineHolder.split(",");
                    ObjectInfo objectInfo = new ObjectInfo(Integer.parseInt(bucketsHolder[0]), Integer.parseInt(bucketsHolder[1]), Integer.parseInt(bucketsHolder[2]));
                    this.heap.put(objectInfo.getId(), objectInfo);
                }
            } catch (Exception readLineException) {
                System.out.println("ReadLine Exception");
                System.exit(1);
            }
            parser.close();
        } catch (Exception e) {
            System.out.println("IO Exception");
            System.exit(1);
        }

        this.readPointers();
        return this.heap;
    }

    /**
     * @return the roots objects identifiers on the heap
     */
    public ArrayList<Integer> getRoots() {
        ArrayList<Integer> roots = new ArrayList<>();
        try {
            File file = new File(paths[1]);
            FileReader fr = new FileReader(file);
            BufferedReader parser = new BufferedReader(fr);
            String lineHolder;

            try {
                while ((lineHolder = parser.readLine()) != null) {
                    roots.add(Integer.parseInt(lineHolder));
                }
            } catch (Exception readLineException) {
                System.out.println("ReadLine Exception");
                System.exit(1);
            }
            parser.close();
        } catch (Exception e) {
            System.out.println("IO Exception");
            System.exit(1);
        }

        return roots;
    }

    /**
     * @function modify the hierarchy between objects in heap according to the pointers file
     */
    public void readPointers() {
        try {
            File file = new File(paths[2]);
            FileReader fr = new FileReader(file);
            BufferedReader parser = new BufferedReader(fr);
            String lineHolder;
            String[] bucketsHolder;

            try {
                while ((lineHolder = parser.readLine()) != null) {
                    bucketsHolder = lineHolder.split(",");
                    this.heap.get(Integer.parseInt(bucketsHolder[0])).addChild(heap.get(Integer.parseInt(bucketsHolder[1])));
                }
            } catch (Exception readLineException) {
                System.out.println("ReadLine Exception");
                System.exit(1);
            }
            parser.close();
        } catch (Exception e) {
            System.out.println("IO Exception");
            System.exit(1);
        }
    }

    /**
     * @return FileWriter object for the Destination file
     */
    public FileWriter getDestinationFile(String fileName) {
        FileWriter destinationFile = null;
        try {
            destinationFile = new FileWriter(paths[3] +"\\"+ fileName);
        } catch (Exception e) {
            System.out.println("IO Exception");
            System.exit(1);
        }
        return destinationFile;
    }
}
