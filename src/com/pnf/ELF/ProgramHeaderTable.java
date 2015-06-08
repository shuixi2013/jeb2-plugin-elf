package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class ProgramHeaderTable extends StreamReader {

    private int phoff;
    private int phentsize;
    private int phnum;
    private List<ProgramHeader> entries = new ArrayList<>();
    public ProgramHeaderTable(byte[] data, int phoff, int phentsize, int phnum) {

        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(phoff);

        for(int index=0; index < phnum; index++) {
             entries.add(new ProgramHeader(data, phoff + phentsize * index));
        }


    }

}
