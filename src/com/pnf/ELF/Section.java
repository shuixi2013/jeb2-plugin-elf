package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.Arrays;


public class Section extends StreamReader {

    protected int size;
    protected int offset;
    protected StringTableSection nameTable;
    protected ByteArrayInputStream stream;
    protected byte[] data;
    
    public Section(byte[] data, int size, int offset) {
        // Setup member variables and set stream to start of section
        stream = new ByteArrayInputStream(data);
        stream.skip(offset);
        this.size = size;
        this.offset = offset;
        this.data = data;
    }

    public byte[] getBytes() {
        return Arrays.copyOfRange(data, offset, offset + size);
    }

    public int getOffset() {
        return offset;
    }
    public int getSize() {
        return size;
    }
    public void setNameTable(StringTableSection nameTable) {
        this.nameTable = nameTable;
    }
}
