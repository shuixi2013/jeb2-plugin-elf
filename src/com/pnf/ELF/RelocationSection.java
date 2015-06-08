package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

public class RelocationSection extends Section {
    private int s_size;
    private int s_offset;
    private int s_entsize;
    private boolean RELA;

    private List<RelocationSectionEntry> entries = new ArrayList<>();

    public RelocationSection(byte[] data, int s_size, int s_offset, int s_entsize, boolean RELA) {
        super(data, s_size, s_offset);
        this.s_entsize = s_entsize;
        this.RELA = RELA;

        for(int index=0; index < s_size / s_entsize; index++) {
            entries.add(new RelocationSectionEntry(data, s_entsize, s_offset + s_entsize * index, RELA));
        }
    }
}
