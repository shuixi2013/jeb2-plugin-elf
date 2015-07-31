package com.pnf.ELFPlugin;

import com.pnf.ELF.ELF;
import com.pnf.ELF.ProgramHeader;
import com.pnf.ELF.SectionHeader;
import com.pnfsoftware.jeb.core.units.codeobject.ISegmentInformation;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ELFSectionInfo implements ISegmentInformation {
    private static final ILogger logger = GlobalLog.getLogger(ELFSectionInfo.class);

    private String name;
    private int flags;
    private long fileOffset;
    private long memOffset;
    private long fileSize;
    private long memSize;

    public ELFSectionInfo(SectionHeader elfsection) {
        name = elfsection.getName();
        int elfFlags = elfsection.getFlags();
        this.flags = ((elfFlags & ELF.SHF_EXECINSTR) != 0 ? FLAG_EXECUTE : 0) |
                     ((elfFlags & ELF.SHF_ALLOC) != 0 ? FLAG_READ : 0) |
                     ((elfFlags & ELF.SHF_WRITE) != 0 ? FLAG_WRITE : 0);
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
    public ELFSectionInfo(ProgramHeader elfsection) {
        name = "";
        int elfFlags = elfsection.getFlags();
        this.flags = ((elfFlags & ELF.PF_X) != 0 ? FLAG_EXECUTE : 0) |
                     ((elfFlags & ELF.PF_R) != 0 ? FLAG_READ : 0) |
                     ((elfFlags & ELF.PF_W) != 0 ? FLAG_WRITE : 0);
        fileOffset = elfsection.getOffsetInFile();
        memOffset = elfsection.getVirtualAddress();
        fileSize = elfsection.getSizeInFile();
        memSize = elfsection.getSizeInMemory();
    }

    @Override
    public int getFlags() {
        return flags;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public long getOffsetInFile() {
        return fileOffset;
    }
    @Override
    public long getOffsetInMemory() {
        return memOffset;
    }

    @Override
    public long getSizeInFile() {
        return fileSize;
    }
    @Override
    public long getSizeInMemory() {
        return memSize;
    }

}
