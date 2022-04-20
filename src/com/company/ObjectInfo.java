package com.company;

import java.util.ArrayList;
import java.util.List;

public class ObjectInfo {

    private final int Id;
    private int memStart;
    private int memEnd;
    private boolean IsMark;
    int size;
    private final List<ObjectInfo> references = new ArrayList<>();

    public ObjectInfo(int identifier, int memStart, int memEnd) {
        this.Id = identifier;
        this.memStart = memStart;
        this.memEnd = memEnd;
        this.IsMark = false;
        this.size = memEnd - memStart;
    }

    /**
     * returns the object identifier
     */
    public int getId() {
        return Id;
    }

    /**
     * returns index of the first byte of the object
     */
    public int getMemStart() {
        return memStart;
    }

    /**
     * returns index of the last byte of the object
     */
    public int getMemEnd() {
        return memEnd;
    }

    /**
     * function shifts the object's memory bounds to given index
     * returns the index of the next byte of memory
     */
    public int move(int startIndex) {
        int offset = startIndex - memStart;
        memStart = startIndex;
        memEnd += offset;
        return memEnd + 1;
    }

    /**
     * returns whether the object is marked or not
     */
    public boolean isMarked() {
        return IsMark;
    }

    /**
     * set the current object as marked
     */
    public void setMarked() {
        IsMark = true;
    }

    /**
     * returns current object references list
     */
    public List<ObjectInfo> getRef() {
        return references;
    }

    /**
     * adds child reference to the current heap object
     *
     * @param child to be added to this object
     */
    public void addChild(ObjectInfo child) {
        references.add(child);
    }

    /**
     * Returns the result objects as a string
     */
    public String toString() {
        return Id + "," + memStart + "," + memEnd;
    }
}

