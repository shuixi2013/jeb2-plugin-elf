package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class SymbolTableEntry extends StreamReader {
    protected ByteArrayInputStream stream;

    protected int name;
    protected String nameString;
    protected int value;
    protected int size;
    protected int info;
    protected int other;
    protected short sectionHeaderIndex;
    protected int bind;
    protected String bindString;
    protected int type;
    protected String typeString;

    public SymbolTableEntry(byte[] data, int offset) {
        stream = new ByteArrayInputStream(data);
        stream.skip(offset);

        name = readInt(stream);

        value = readInt(stream);
        size = readInt(stream);
        info = stream.read();
        other = stream.read();
        sectionHeaderIndex = readShort(stream);
        bind = info >> 4;
        switch(bind) {
            case ELF.STB_LOCAL:
                bindString = "STB_LOCAL";
                break;
            case ELF.STB_GLOBAL:
                bindString = "STB_GLOBAL";
                break;
            case ELF.STB_WEAK:
                bindString = "STB_WEAK";
                break;
            case ELF.STB_LOPROC:
                bindString = "STB_LOPROC";
                break;
            case ELF.STB_HIPROC:
                bindString = "STB_HIPROC";
                break;
            default:
                bindString = "UNKNOWN";
        }
        type = info & 0xf;
        switch(type) {
            case ELF.STT_NOTYPE:
                typeString = "STT_NOTYPE";
                break;
            case ELF.STT_OBJECT:
                typeString = "STT_OBJECT";
                break;
            case ELF.STT_FUNC:
                typeString = "STT_FUNC";
                break;
            case ELF.STT_SECTION:
                typeString = "STT_SECTION";
                break;
            case ELF.STT_FILE:
                typeString = "STT_FILE";
                break;
            case ELF.STT_LOPROC:
                typeString = "STT_LOPROC";
                break;
            case ELF.STT_HIPROC:
                typeString = "STT_HIPROC";
                break;
            default:
                typeString = "UNKNOWN";
        }
    } 

    public void setName(StringTableSection nameTable) {
        this.nameString = nameTable.getString(name);
    } 
    public String getName() {
         return nameString;
    }

    public int getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public int getInfo() {
        return info;
    }

    public int getOther() {
        return other;
    }


    public short getSectionHeaderIndex() {
        return sectionHeaderIndex;
    }

    public String getBindString() {
        return bindString;
    }

    public String getTypeString() {
        return typeString;
    }
}
