package com.pnf.ELF;

public class DynamicSection extends Section {

    private int tag;
    private int un;
    private int val;
    private int ptr;

    public DynamicSection(byte[] data, int s_size, int s_offset) {
        super(data, s_size, s_offset);

        tag = readInt(stream);
        un = readInt(stream);

        switch(tag) {
            case ELF.DT_NULL:
                val = un;
                break;
            case ELF.DT_NEEDED:
                val = un;
                break;
            case ELF.DT_PLTRELSZ:
                ptr = un;
                break;
            case ELF.DT_PLTGOT:
                ptr = un;
                break;
            case ELF.DT_HASH:
                ptr = un;
                break;
            case ELF.DT_STRTAB:
                ptr = un;
                break;
            case ELF.DT_SYMTAB:
                ptr = un;
                break;
            case ELF.DT_RELA:
                ptr = un;
                break;
            case ELF.DT_RELASZ:
                val = un;
                break;
            case ELF.DT_RELAENT:
                val = un;
                break;
            case ELF.DT_STRSZ:
                val = un;
                break;
            case ELF.DT_SYMENT:
                val = un;
                break;
            case ELF.DT_INIT:
                ptr = un;
                break;
            case ELF.DT_FINI:
                ptr = un;
                break;
            case ELF.DT_SONAME:
                val = un;
                break;
            case ELF.DT_RPATH:
                val = un;
                break;
            case ELF.DT_SYMBOLIC:
                break;
            case ELF.DT_REL:
                ptr = un;
                break;
            case ELF.DT_RELSZ:
                val = un;
                break;
            case ELF.DT_RELENT:
                val = un;
                break;
            case ELF.DT_PLTREL:
                val = un;
                break;
            case ELF.DT_DEBUG:
                ptr = un;
                break;
            case ELF.DT_TEXTREL:
                break;
            case ELF.DT_JMPREL:
                ptr = un;
                break;
            case ELF.DT_LOPROC:
                break;
            case ELF.DT_HIPROC:
                break;

        }
    }
}
