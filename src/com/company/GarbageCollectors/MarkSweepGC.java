package com.company.GarbageCollectors;

import com.company.HeapConstructor;
import com.company.ObjectInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MarkSweepGC {

    public static void mark(ArrayList<Integer> roots, HashMap<Integer, ObjectInfo> heap) {
        ArrayList<Integer> markedObjects = new ArrayList<>();
        for (int root : roots) {
            for (ObjectInfo reference : heap.get(root).getRef()) {
                reference.setMarked();
            }
        }
    }

    public static void sweep(HashMap<Integer, ObjectInfo> heap){
        heap.forEach((id,objectInfo) -> {
            if(!objectInfo.isMarked()){
                heap.remove(id);
            }
        });
    }

    public static void writeOut(FileWriter destinationFile,HashMap<Integer, ObjectInfo> heap) throws IOException {
        StringBuilder sb = new StringBuilder();
        heap.forEach((id,objectInfo) -> {
            sb.append(objectInfo.toCSVLine());
        });
        destinationFile.write(sb.toString());
    }

    public static void main(String[] args) throws Exception {
        HashMap<Integer, ObjectInfo> heap;
        ArrayList<Integer> roots;
        FileWriter destinationFile;
        try {
            HeapConstructor heapConstructor = new HeapConstructor(args);
            heap = heapConstructor.getHeap();
            roots = heapConstructor.getRoots();
            destinationFile = heapConstructor.getDestinationFile();
            mark(roots, heap);
            sweep(heap);
            writeOut(destinationFile, heap);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
