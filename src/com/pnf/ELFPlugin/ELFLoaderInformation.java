package com.pnf.ELFPlugin;

import com.pnf.ELF.ELF;
import com.pnf.ELF.ELFFile;
import com.pnfsoftware.jeb.core.units.codeobject.ILoaderInformation;
import com.pnfsoftware.jeb.core.units.codeobject.ProcessorType;
import com.pnfsoftware.jeb.core.units.codeobject.SubsystemType;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;


public class ELFLoaderInformation implements ILoaderInformation {
    private static final ILogger logger = GlobalLog.getLogger(ELFLoaderInformation.class);


    public static int HAS_RELOCATIONS;
    public static int HAS_SYMBOLS;

    public long compTimestamp;
    public long entryPoint;
    public int flags;
    public long imageBase;
    public long imageSize;
    public ProcessorType targetProcessor;
    public SubsystemType targetSubsystem;
    public int wordSize;
    public boolean isLibrary;
    public boolean isLittleEndian;

    public ELFLoaderInformation(ELFFile elf) {

        wordSize = elf.getWordSize();
        entryPoint = elf.getEntryPoint();
        flags = elf.getFlags();
        imageBase = elf.getImageBase();
        imageSize = elf.getImageSize();
        isLibrary = (elf.getType() == ELF.ET_DYN);
        isLittleEndian = elf.isLittleEndian();
        switch(elf.getArch()) {
            case ELF.EM_ARM:
                if(wordSize == 32)
                    targetProcessor = ProcessorType.ARM;
                else if(wordSize == 64)
                    targetProcessor = ProcessorType.ARM64;
                break;
            case ELF.EM_MIPS:
                if(wordSize == 32)
                    targetProcessor = ProcessorType.MIPS;
                else if(wordSize == 64)
                    targetProcessor = ProcessorType.MIPS64;
                break;
            case ELF.EM_X86_64:
                targetProcessor = ProcessorType.X86_64;
                break;
            case ELF.EM_386:
                targetProcessor = ProcessorType.X86;
                break;
            default:
                targetProcessor = null;
        }


        // Always
        targetSubsystem = SubsystemType.LINUX;

        // Unused
        compTimestamp = 0;
    }

    public long getCompilationTimestamp() {
        return compTimestamp;
    }
    public long getEntryPoint() {
        return entryPoint;
    }
    public int getFlags() {
        return flags;
    }
    public long getImageBase() {
        return imageBase;
    }
    public long getImageSize() {
        return imageSize;
    }
    public ProcessorType getTargetProcessor() {
        return targetProcessor;
    }
    public SubsystemType getTargetSubsystem() {
        return targetSubsystem;
    }
    public int getWordSize() {
        return wordSize;
    }
    public boolean isLibraryFile() {
        return true;
    }
    public boolean isLittleEndian() {
        return true;
    }


}
