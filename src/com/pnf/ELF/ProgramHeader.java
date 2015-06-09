package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class ProgramHeader extends StreamReader {

    private int type;
    private String typeString;
    public int getType() {
		return type;
	}

	public String getTypeString() {
		return typeString;
	}

	public int getOffset() {
		return offset;
	}

	public int getVaddr() {
		return vaddr;
	}

	public int getPaddr() {
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

	public int getAlign() {
		return align;
	}

	public Section getSection() {
		return section;
	}

	private int offset;
    private int vaddr;
    private int paddr;
    private int fileSize;
    private int memorySize;
    private int flags;
    private int align;
    private Section section;

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
        align = readInt(stream);

        switch(type) {
            case ELF.PT_NULL:
                typeString = "PT_NULL";
                break;
            case ELF.PT_LOAD:
                typeString = "PT_LOAD";
                break;
            case ELF.PT_DYNAMIC:
                typeString = "PT_DYNAMIC";
                break;
            case ELF.PT_INTERP:
                typeString = "PT_INTERP";
                break;
            case ELF.PT_NOTE:
                typeString = "PT_NOTE";
                break;
            case ELF.PT_SHLIB:
                typeString = "PT_SHLIB";
                break;
            case ELF.PT_PHDR:
                typeString = "PT_PHDR";
                break;
            case ELF.PT_LOPROC:
                typeString = "PT_LOPROC";
                break;
            case ELF.PT_HIPROC:
                typeString = "PT_HIPROC";
                break;
            default:
                typeString = "UNKNOWN";
        }
    }

}
