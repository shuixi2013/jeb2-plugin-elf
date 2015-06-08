package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableSection extends Section {

    private int sh_entsize;
    private List<SymbolTableEntry> entries = new ArrayList<>();

    private StringTableSection nameTable;

    public SymbolTableSection(byte[] data, int sh_size, int sh_offset, int sh_entsize, StringTableSection nameTable) {
        super(data, sh_size, sh_offset);
        this.sh_entsize = sh_entsize;
        this.nameTable = nameTable;
        for(int index=0; index < sh_size / sh_entsize; index++) {
            entries.add(new SymbolTableEntry(data, sh_offset + index * sh_entsize));
        }
    }


    public SymbolTableEntry getEntry(int index) {
        return entries.get(index);
    }
    public List<SymbolTableEntry> getEntries() {
        return entries;
    }

    @Override
    public void setNameTable(StringTableSection nameTable) {
        super.setNameTable(nameTable);
        for(SymbolTableEntry entry : entries) {
            entry.setName(nameTable);
        }
    }

}
