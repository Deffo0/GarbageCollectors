package com.company.GarbageCollectors;

import com.company.HeapConstructor;
import com.company.ObjectInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarkCompactGC {
    public static void mark(ArrayList<ObjectInfo> roots) {
        for (ObjectInfo root : roots) {
            if(root.isMarked()){
                continue;
            }
            root.setMarked();
            mark(root.getRef());
        }
    }

    public static void sweep(HashMap<Integer, ObjectInfo> heap){
        ArrayList<Integer> unmarkedObjects = new ArrayList<>();
        for (int id:heap.keySet()) {
            if(!heap.get(id).isMarked()){
                unmarkedObjects.add(id);
            }
        }
        for (int unmarkedObject:unmarkedObjects) {
            heap.remove(unmarkedObject);
        }
    }

    public static void writeOut(FileWriter destinationFile,HashMap<Integer, ObjectInfo> heap) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int id:heap.keySet()) {
            sb.append(heap.get(id).toCSVLine());
        }
        destinationFile.write(sb.toString());
        destinationFile.close();
    }

    private static List<ObjectInfo> compact(List<Integer> roots, HashMap<Integer, ObjectInfo> GarbageHeap) {
        List<ObjectInfo> CleanedHeap = new ArrayList<>();

        int nextIndex = 0;

        if (roots.isEmpty()) return CleanedHeap;


        for (Integer ID : roots) {
            ObjectInfo MemObj = GarbageHeap.get(ID);
            nextIndex = MemObj.move(nextIndex);
            CleanedHeap.add(MemObj);
            MemObj.setMarked();
        }

        return CleanedHeap;
    }

    public static void main(String[] args) throws Exception {
        HashMap<Integer, ObjectInfo> heap ;
        ArrayList<Integer> roots ;
        ArrayList<ObjectInfo> roots_objects  = new ArrayList<>();
        FileWriter destinationFile;
        try {
            HeapConstructor heapConstructor = new HeapConstructor(args);
            heap = heapConstructor.getHeap();
            roots = heapConstructor.getRoots();
            destinationFile = heapConstructor.getDestinationFile("MarkCompactGC.csv");
            for (int id:heapConstructor.getRoots()) {
                roots_objects.add(heap.get(id));
            }
            mark(roots_objects);
            sweep(heap);
            compact(roots, heap);
            writeOut(destinationFile, heap);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}
