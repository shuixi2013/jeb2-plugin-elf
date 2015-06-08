package com.pnf.ELF;

import java.io.ByteArrayInputStream;


public class Section extends StreamReader {

    protected int s_size;
    protected int s_offset;
    protected ByteArrayInputStream stream;
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
    }

    public byte[] getBytes() {
        byte[] output = new byte[s_size];
        stream.reset();
        stream.skip(s_offset);
        stream.read(output, 0, s_size);
        return output;
    }

    public int getOffset() {
        return s_offset;
    }
    public int getSize() {
        return s_size;
    }
}
