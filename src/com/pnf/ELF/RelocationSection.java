package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

public class RelocationSection extends Section {
    private int size;
    private int offset;
    private int entrySize;
    private boolean RELA;
    private SymbolTableSection symtab;

    private List<RelocationSectionEntry> entries = new ArrayList<>();

    public RelocationSection(byte[] data, int s_size, int s_offset, int entrySize, boolean RELA) {
        super(data, s_size, s_offset);
        this.entrySize = entrySize;
        this.RELA = RELA;


        for(int index=0; index < s_size / entrySize; index++) {
            entries.add(new RelocationSectionEntry(data, entrySize, s_offset + entrySize * index, RELA));
        }
    }

    public void setSymbolTable(SectionHeader header) {
        symtab = (SymbolTableSection)header.getSection();
        for(RelocationSectionEntry entry : entries) {
            entry.setSymbolTable(header);
        }
    }

    public List<RelocationSectionEntry> getEntries() {
        return entries;
    }
}
