package com.pnf.ELFPlugin;

import com.pnfsoftware.jeb.core.units.codeobject.ISymbolInformation;
import com.pnfsoftware.jeb.core.units.codeobject.SymbolType;
import static com.pnf.ELF.ELF.*;


public class SymbolInfo implements ISymbolInformation {

    private String name;
    private long address;
    private long identifier;
    private SymbolType type;
    private long size;
    public SymbolInfo(String name, long address, int type, int size) {
        this.name = name;
        this.address = address;
        this.identifier = identifier;
        this.size = (int)size;

        switch(type) {
            case STT_FUNC:
                this.type = SymbolType.FUNCTION;
                break;
            case STT_SECTION:
                this.type = SymbolType.SECTION;
                break;
            case STT_FILE:
                this.type = SymbolType.FILE;
                break;
            case STT_OBJECT:
                this.type = SymbolType.OBJECT;
                break;
        }
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
        return type;
    }
    public long getSize() {
        return -1;
    }

}
