package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.Arrays;


public class Section extends StreamReader {

    protected int s_size;
    protected int s_offset;
    protected StringTableSection nameTable;
    protected ByteArrayInputStream stream;
    protected byte[] data;
    /**
     * @param s_type
     * @param s_size
     * @param s_offset
     * @param s_name
     * @param s_name_s
     * @param s_nameTable
     */
    public Section(byte[] data, int s_size, int s_offset) {
        // Setup member variables and set stream to start of section
        stream = new ByteArrayInputStream(data);
        stream.skip(s_offset);
        this.s_size = s_size;
        this.s_offset = s_offset;
        this.data = data;
    }

    public byte[] getBytes() {
        return Arrays.copyOfRange(data, s_offset, s_offset + s_size);
    }

    public int getOffset() {
        return s_offset;
    }
    public int getSize() {
        return s_size;
    }
    public void setNameTable(StringTableSection nameTable) {
        this.nameTable = nameTable;
    }
}
