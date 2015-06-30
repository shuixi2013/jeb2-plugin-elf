package com.pnf.ELFPlugin;

import com.pnf.ELF.ELF;
import com.pnf.ELF.SectionHeader;
import com.pnfsoftware.jeb.core.units.codeobject.ISegmentInformation;

public class ELFSectionInfo implements ISegmentInformation {

    public static int FLAG_READ;
    public static int FLAG_EXECTUTE;
    public static int FLAG_WRITE;

    private String name;
    private int flags;
    private long fileOffset;
    private long memOffset;
    private long fileSize;
    private long memSize;

    public ELFSectionInfo(SectionHeader elfsection) {
        name = elfsection.getName();
        flags = elfsection.getFlags();
        fileOffset = elfsection.getOffset();
        memOffset = elfsection.getAddress();
        if(elfsection.getType() == ELF.SHT_NOBITS) {
            fileSize = 0;
            memSize = elfsection.getSize();
        }
        else {
            fileSize = elfsection.getSize();
            memSize = elfsection.getSize();
        }
    }

    public ELFSectionInfo(String name, int flags, long fileOffset, long memOffset, long fileSize, long memSize) {
        this.name = name;
        this.flags = flags;
        this.fileOffset = fileOffset;
        this.memOffset = memOffset;
        this.fileSize = fileSize;
        this.memSize = memSize;
    }

    public int getFlags() {
        return FLAG_READ << 2 + FLAG_WRITE << 1 + FLAG_EXECUTE;
    }
    public String getName() {
        return name;
    }
    public long getOffsetInFile() {
        return fileOffset;
    }
    public long getOffsetInMemory() {
        return memOffset;
    }

    public long getSizeInFile() {
        return fileSize;
    }
    public long getSizeInMemory() {
        return memSize;
    }

}
