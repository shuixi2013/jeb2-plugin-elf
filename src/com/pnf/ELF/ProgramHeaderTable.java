package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class ProgramHeaderTable extends StreamReader {

    private int offset;
    private int entrySize;
    private int number;
    private List<ProgramHeader> entries = new ArrayList<>();
    public ProgramHeaderTable(byte[] data, int offset, int entrySize, int number) {

        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(offset);

        for(int index=0; index < number; index++) {
             entries.add(new ProgramHeader(data, offset + entrySize * index));
        }


    }

    public List<ProgramHeader> getHeaders() {
        return entries;
    }
}
