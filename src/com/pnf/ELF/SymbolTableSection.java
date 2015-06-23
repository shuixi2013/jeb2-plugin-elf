package com.pnf.ELF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SymbolTableSection extends Section {

	// Wraps the bytes of a Symbol Table
	// Keeps a list of entries for the data
	
    private int entrySize;
    private List<SymbolTableEntry> entries = new ArrayList<>();

    private StringTableSection nameTable;

    public SymbolTableSection(byte[] data, int size, int offset, int entrySize, StringTableSection nameTable) {
        super(data, size, offset);
        this.entrySize = entrySize;
        this.nameTable = nameTable;
        for(int index=0; index < size / entrySize; index++) {
            entries.add(new SymbolTableEntry(data, offset + index * entrySize));
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

    public HashMap<Integer, String> toHashMap() {
        HashMap<Integer, String> output = new HashMap<>();
        for(SymbolTableEntry entry : entries) {
            if(entry.getType() == ELF.STT_FUNC)
                output.put(entry.getValue(), entry.getName());
        }
        return output;
    }

}
