package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class ProgramHeader extends StreamReader {

    private int p_type;
    private String p_type_s;
    private int p_offset;
    private int p_vaddr;
    private int p_paddr;
    private int p_filesz;
    private int p_memsz;
    private int p_flags;
    private int p_align;
    private Section section;

    private static final int PT_NULL = 0;
    private static final int PT_LOAD = 1;
    private static final int PT_DYNAMIC = 2;
    private static final int PT_INTERP = 3;
    private static final int PT_NOTE = 4;
    private static final int PT_SHLIB = 5;
    private static final int PT_PHDR = 6;
    private static final int PT_LOPROC = 0x70000000;
    private static final int PT_HIPROC = 0x7fffffff;

    public ProgramHeader(byte[] data, int offset) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(offset);
        p_type = readInt(stream);
        p_offset = readInt(stream);
        p_vaddr = readInt(stream);
        p_paddr = readInt(stream);
        p_filesz = readInt(stream);
        p_memsz = readInt(stream);
        p_flags = readInt(stream);
        p_align = readInt(stream);

        switch(p_type) {
            case PT_NULL:
                p_type_s = "PT_NULL";
                break;
            case PT_LOAD:
                p_type_s = "PT_LOAD";
                break;
            case PT_DYNAMIC:
                p_type_s = "PT_DYNAMIC";
                break;
            case PT_INTERP:
                p_type_s = "PT_INTERP";
                break;
            case PT_NOTE:
                //section = new NoteSection(data, p_size, p_offset);
                p_type_s = "PT_NOTE";
                break;
            case PT_SHLIB:
                p_type_s = "PT_SHLIB";
                break;
            case PT_PHDR:
                p_type_s = "PT_PHDR";
                break;
            case PT_LOPROC:
                p_type_s = "PT_LOPROC";
                break;
            case PT_HIPROC:
                p_type_s = "PT_HIPROC";
                break;
            default:
                p_type_s = "UNKNOWN";
        }
    }

}
