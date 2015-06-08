package com.pnf.ELF;

import java.io.ByteArrayInputStream;


public class RelocationSectionEntry extends StreamReader {

    private int se_size;
    private int se_offset;

    private int r_offset;
    private int r_info;
    private int r_addend;

    private int r_sym;
    private int r_type;
    private boolean RELA;

    public RelocationSectionEntry(byte[] data, int se_size, int se_offset, boolean RELA) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(se_offset);
        this.se_size = se_size;
        this.se_offset = se_offset;
        r_offset = readInt(stream);
        r_info = readInt(stream);
        // Not always set
        if(RELA) {
            r_addend = readInt(stream);
        }

        r_sym = r_info >> 8;
        r_type = (char)r_info;
    }
}
