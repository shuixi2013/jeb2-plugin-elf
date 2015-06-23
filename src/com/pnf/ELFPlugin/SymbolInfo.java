package com.pnf.ELFPlugin;

import com.pnfsoftware.jeb.core.units.codeloader.ISymbolInformation;
import com.pnfsoftware.jeb.core.units.codeloader.SymbolType;


public class SymbolInfo implements ISymbolInformation {

    private String name;
    private long address;
    private long identifier;
    public SymbolInfo(String name, long address, long identifier) {
        this.name = name;
        this.address = address;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public long getAddress() {
        return address;
    }

    public long getIdentifier() {
        return identifier;
    }

    public long getOffset() {
        return -1;
    }
    public int getFlags() {
        return -1;
    }

    public SymbolType getType() {
        return null;
    }
    public long getSize() {
        return -1;
    }

}
