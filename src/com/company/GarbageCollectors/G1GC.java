package com.company.GarbageCollectors;

import com.company.HeapConstructor;
import com.company.ObjectInfo;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class G1GC {
	private static final int numOfRegions = 16;

	public static void main(String[] args) throws Exception {
		HashMap<Integer, ObjectInfo> heap;
		ArrayList<ObjectInfo> roots = new ArrayList<>();
		FileWriter destinationFile;
		try {
			int totalSize = Integer.parseInt(args[0]);
			int regionSize = totalSize / numOfRegions;
			int regions[] = new int[numOfRegions + 1];
			Arrays.fill(regions, regionSize);
			String[] files = Arrays.copyOfRange(args, 1, args.length);
			HeapConstructor heapConstructor = new HeapConstructor(files);
			heap = heapConstructor.getHeap();

			for (int id : heapConstructor.getRoots()) {
				roots.add(heap.get(id));
			}
			destinationFile = heapConstructor.getDestinationFile("G1GC.csv");
			setRegions(heap, regions, regionSize);
			markAndSweep(heap, roots);
			setRegions(heap, regions, regionSize);
			defragment(heap, regions, regionSize);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	private static void defragment(HashMap<Integer, ObjectInfo> heap, int[] regions, int regionSize) {
		// TODO Auto-generated method stub

	}

	private static void setRegions(HashMap<Integer, ObjectInfo> heap, int[] regions, int regionSize) {
		// TODO Auto-generated method stub
//		System.out.println("regionSize: "+ regionSize);
		for (ObjectInfo object : heap.values()) {
			//
			int regionNum = getObjectRegion(object.getMemStart(), regionSize);

			regions[regionNum] -= (object.getSize());
//			System.out.println(object.getId()+" regionNum " + regionNum );
		}

	}

	private static int getObjectRegion(int memStart, int regionSize) {
		int regionNum = (int) Math.ceil((double) memStart / regionSize);
		if (memStart % numOfRegions == 0)
			regionNum++;
		return regionNum;
	}

	private static void markAndSweep(HashMap<Integer, ObjectInfo> heap, ArrayList<ObjectInfo> roots) {
		// TODO Auto-generated method stub
		MarkSweepGC.mark(roots);
		MarkSweepGC.sweep(heap);
	}
}
