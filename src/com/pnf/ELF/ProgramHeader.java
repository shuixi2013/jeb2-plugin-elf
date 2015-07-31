package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.pnfsoftware.jeb.core.units.IUnitNotification;
import com.pnfsoftware.jeb.core.units.NotificationType;
import com.pnfsoftware.jeb.core.units.UnitNotification;

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

    public int getOffsetInFile() {
        return offset;
    }

    public int getVirtualAddress() {
        return vaddr;
    }

    public int getPhysicalAddress() {
        return paddr;
    }

    public int getSizeInFile() {
        return fileSize;
    }

    public int getSizeInMemory() {
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

    public ProgramHeader(byte[] data, int sectionOffset, List<UnitNotification> notifications) {
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

        typeString = ELF.getPTString(type);

        if(type == ELF.PT_GNU_STACK && (flags & ELF.PF_X) != 0) {
            notifications.add(new UnitNotification(NotificationType.AREA_OF_INTEREST, "Stack is executable"));
        }
    }
}
