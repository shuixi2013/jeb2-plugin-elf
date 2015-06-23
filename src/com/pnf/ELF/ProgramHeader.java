package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class ProgramHeader extends StreamReader {

    private int type;
    private String typeString;
    private int offset;
    private int vaddr;
    private int paddr;
    private int fileSize;
    private int memorySize;
    private int flags;
    private String flagsString;
    private int align;
    private Section section;

    public int getType() {
        return type;
    }

    public String getTypeString() {
        return typeString;
    }

    public int getOffset() {
        return offset;
    }

    public int getVAddr() {
        return vaddr;
    }

    public int getPAddr() {
        return paddr;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public int getFlags() {
        return flags;
    }
    public String getFlagsString() {
        return flagsString;
    }

    public int getAlign() {
        return align;
    }

    public Section getSection() {
        return section;
    }

    public ProgramHeader(byte[] data, int sectionOffset) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(sectionOffset);
        type = readInt(stream);
        offset = readInt(stream);
        vaddr = readInt(stream);
        paddr = readInt(stream);
        fileSize = readInt(stream);
        memorySize = readInt(stream);
        flags = readInt(stream);

        flagsString = "";
        flagsString = String.format("%c%c%c", 
                (flags & ELF.PF_X) != 0 ? 'E' : ' ',
                (flags & ELF.PF_W) != 0 ? 'W' : ' ',
                (flags & ELF.PF_R) != 0 ? 'R' : ' ');

        align = readInt(stream);

        typeString = ELF.getSegmentType(type);
    }

}
