package com.company.GarbageCollectors;

import com.company.HeapConstructor;
import com.company.ObjectInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    public static void writeOut(FileWriter destinationFile,List <ObjectInfo> heap) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (ObjectInfo objectInfo:heap) {
            sb.append(objectInfo.toCSVLine());
        }
        destinationFile.write(sb.toString());
        destinationFile.close();
    }

    private static List<ObjectInfo> compact(HashMap<Integer, ObjectInfo> GarbageHeap) {
        List<ObjectInfo> CleanedHeap = new ArrayList<>();
        ArrayList<ObjectInfo> sortedHeap = new ArrayList<>();
        int nextIndex = 0;
        if (GarbageHeap.isEmpty()) return CleanedHeap;

        for (int id:GarbageHeap.keySet()) {
            sortedHeap.add(GarbageHeap.get(id));
        }
        sortedHeap.sort(Comparator.comparingInt(ObjectInfo::getMemStart));

        for (ObjectInfo MemObj : sortedHeap) {
            nextIndex = MemObj.move(nextIndex);
            CleanedHeap.add(MemObj);
            MemObj.setMarked();
        }

        return CleanedHeap;
    }

    public static void main(String[] args) {
        HashMap<Integer, ObjectInfo> heap ;
        ArrayList<ObjectInfo> roots_objects  = new ArrayList<>();
        FileWriter destinationFile;
        List<ObjectInfo> compactedHeap;
        try {
            HeapConstructor heapConstructor = new HeapConstructor(args);
            heap = heapConstructor.getHeap();
            destinationFile = heapConstructor.getDestinationFile("MarkCompactGC.csv");
            for (int id:heapConstructor.getRoots()) {
                roots_objects.add(heap.get(id));
            }
            mark(roots_objects);
            sweep(heap);
            compactedHeap = compact(heap);
            writeOut(destinationFile, compactedHeap);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}
