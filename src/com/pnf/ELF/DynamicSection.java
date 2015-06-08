package com.pnf.ELF;

public class DynamicSection extends Section {

    private int d_tag;
    private int d_un;
    private int d_val;
    private int d_ptr;

    private static final int DT_NULL = 0;
    private static final int DT_NEEDED = 1;
    private static final int DT_PLTRELSZ = 2;
    private static final int DT_PLTGOT = 3;
    private static final int DT_HASH = 4;
    private static final int DT_STRTAB = 5;
    private static final int DT_SYMTAB = 6;
    private static final int DT_RELA = 7;
    private static final int DT_RELASZ = 8;
    private static final int DT_RELAENT = 9;
    private static final int DT_STRSZ = 10;
    private static final int DT_SYMENT = 11;
    private static final int DT_INIT = 12;
    private static final int DT_FINI = 13;
    private static final int DT_SONAME = 14;
    private static final int DT_RPATH = 15;
    private static final int DT_SYMBOLIC = 16;
    private static final int DT_REL = 17;
    private static final int DT_RELSZ = 18;
    private static final int DT_RELENT = 19;
    private static final int DT_PLTREL = 20;
    private static final int DT_DEBUG = 21;
    private static final int DT_TEXTREL = 22;
    private static final int DT_JMPREL = 23;
    private static final int DT_LOPROC = 0x70000000;
    private static final int DT_HIPROC = 0x7fffffff;

    public DynamicSection(byte[] data, int s_size, int s_offset) {
        super(data, s_size, s_offset);

        d_tag = readInt(stream);
        d_un = readInt(stream);

        switch(d_tag) {
            case DT_NULL:
                d_val = d_un;
                break;
            case DT_NEEDED:
                d_val = d_un;
                break;
            case DT_PLTRELSZ:
                d_ptr = d_un;
                break;
            case DT_PLTGOT:
                d_ptr = d_un;
                break;
            case DT_HASH:
                d_ptr = d_un;
                break;
            case DT_STRTAB:
                d_ptr = d_un;
                break;
            case DT_SYMTAB:
                d_ptr = d_un;
                break;
            case DT_RELA:
                d_ptr = d_un;
                break;
            case DT_RELASZ:
                d_val = d_un;
                break;
            case DT_RELAENT:
                d_val = d_un;
                break;
            case DT_STRSZ:
                d_val = d_un;
                break;
            case DT_SYMENT:
                d_val = d_un;
                break;
            case DT_INIT:
                d_ptr = d_un;
                break;
            case DT_FINI:
                d_ptr = d_un;
                break;
            case DT_SONAME:
                d_val = d_un;
                break;
            case DT_RPATH:
                d_val = d_un;
                break;
            case DT_SYMBOLIC:
                break;
            case DT_REL:
                d_ptr = d_un;
                break;
            case DT_RELSZ:
                d_val = d_un;
                break;
            case DT_RELENT:
                d_val = d_un;
                break;
            case DT_PLTREL:
                d_val = d_un;
                break;
            case DT_DEBUG:
                d_ptr = d_un;
                break;
            case DT_TEXTREL:
                break;
            case DT_JMPREL:
                d_ptr = d_un;
                break;
            case DT_LOPROC:
                break;
            case DT_HIPROC:
                break;

        }
    }
}
