package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

public class DynamicSection extends Section {

    private List<DynamicSectionEntry> entries = new ArrayList<>();
    private SectionHeader stringTable;
    public DynamicSection(byte[] data, int size, int offset, int sh_entsize) {
        super(data, size, offset);

        for(int index=offset; index < offset + size; index += sh_entsize) {

            entries.add(new DynamicSectionEntry(data, sh_entsize, index));
        }

    }
    public List<DynamicSectionEntry> getEntries() {
        return entries;
    }

    public void setStringTable(SectionHeader stringTable) {
        this.stringTable = stringTable;
        for(DynamicSectionEntry entry : entries) {
            entry.setStringTable(stringTable);
        }
    }


}
