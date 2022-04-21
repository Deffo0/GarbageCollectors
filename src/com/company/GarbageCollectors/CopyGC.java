package com.company.GarbageCollectors;

import com.company.HeapConstructor;
import com.company.ObjectInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CopyGC {


    public static void main(String[] args) throws Exception {
        HashMap<Integer, ObjectInfo> heap;
        ArrayList<Integer> roots;
        FileWriter destinationFile;

        try {
            HeapConstructor heapConstructor = new HeapConstructor(args);
            heap = heapConstructor.getHeap();
            roots = heapConstructor.getRoots();
            destinationFile = heapConstructor.getDestinationFile("CopyGC.csv");
            List<ObjectInfo> CleanedList;

            CleanedList = CheneyAlgo(roots, heap);

            writeOut(destinationFile , CleanedList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }


    private static List<ObjectInfo> CheneyAlgo(List<Integer> roots, HashMap<Integer, ObjectInfo> GarbageHeap) {
        List<ObjectInfo> CleanedHeap = new ArrayList<>();

        int nextIndex = 0;

        if (roots.isEmpty()) return CleanedHeap;


        for (Integer ID : roots) {
            ObjectInfo MemObj = GarbageHeap.get(ID);
            if (MemObj.isMarked()) continue;
            nextIndex = MemObj.move(nextIndex);
            CleanedHeap.add(MemObj);
            MemObj.setMarked();
        }

        for (int i = 0; i < CleanedHeap.size(); i++) {
            ObjectInfo parent = CleanedHeap.get(i);

            for (ObjectInfo child : parent.getRef()) {
                if (child.isMarked()) continue;
                nextIndex = child.move(nextIndex);
                CleanedHeap.add(child);
                child.setMarked();
            }
        }

        return CleanedHeap;
    }

    public static void writeOut(FileWriter destinationFile,List<ObjectInfo> CleanedList) throws IOException {

        for (ObjectInfo heapObject : CleanedList) {
            destinationFile.append(heapObject.toCSVLine());
        }
        destinationFile.close();
    }

}
