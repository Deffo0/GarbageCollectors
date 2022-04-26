package com.company.GarbageCollectors;

import com.company.HeapConstructor;
import com.company.ObjectInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class MarkSweepGC {


    private static void mark(ArrayList<ObjectInfo> roots) {
        for (ObjectInfo root : roots) {
            if(root.isMarked()){
                continue;
            }
            root.setMarked();
            mark(root.getRef());
        }
    }

    private static void sweep(HashMap<Integer, ObjectInfo> heap){
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

    private static void writeOut(FileWriter destinationFile,HashMap<Integer, ObjectInfo> heap) throws IOException {
        ArrayList<ObjectInfo> sortedHeap = new ArrayList<>();
        for (int id:heap.keySet()) {
            sortedHeap.add(heap.get(id));
        }
        sortedHeap.sort(Comparator.comparingInt(ObjectInfo::getMemStart));
        StringBuilder sb = new StringBuilder();
        for (ObjectInfo objectInfo:sortedHeap) {
            sb.append(objectInfo.toCSVLine());
        }
        destinationFile.write(sb.toString());
        destinationFile.close();
    }

    public static void main(String[] args) {
        HashMap<Integer, ObjectInfo> heap ;
        ArrayList<ObjectInfo> roots = new ArrayList<>();
        FileWriter destinationFile;
        try {
            HeapConstructor heapConstructor = new HeapConstructor(args);
            heap = heapConstructor.getHeap();
            for (int id:heapConstructor.getRoots()) {
                roots.add(heap.get(id));
            }
            destinationFile = heapConstructor.getDestinationFile("MarkSweepGC.csv");
            mark(roots);
            sweep(heap);
            writeOut(destinationFile, heap);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
