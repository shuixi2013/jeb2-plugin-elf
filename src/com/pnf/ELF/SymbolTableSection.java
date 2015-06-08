package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

public class SymbolTableSection extends Section {

    private int sh_entsize;
    private List<SymbolTableEntry> entries = new ArrayList<>();

    public SymbolTableSection(byte[] data, int sh_size, int sh_offset, int sh_entsize) {
        super(data, sh_size, sh_offset);

        this.sh_entsize = sh_entsize;
    }

    public String getString(int index) {
        return getStringFromTable(stream, index);
    }
}
