package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CopyGC {


     public static void main(String[] args) throws Exception {
         HashMap<Integer, ObjectInfo> heap;
         ArrayList<Integer> roots;
         FileWriter destinationFile;
         try {
             HeapConstructor heapConstructor = new HeapConstructor(args);
             heap = heapConstructor.getHeap();
             roots = heapConstructor.getRoots();
             destinationFile = heapConstructor.getDestinationFile();
         }catch (Exception e){
             System.out.println(e.getMessage());
             System.exit(1);
         }



     }

}
