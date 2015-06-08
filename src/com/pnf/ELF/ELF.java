package com.pnf.ELF;

public class ELF {
    // Holds constants
    public static final int SHT_NULL = 0;
    public static final int SHT_PROGBITS = 1;
    public static final int SHT_SYMTAB = 2;
    public static final int SHT_STRTAB = 3;
    public static final int SHT_RELA = 4;
    public static final int SHT_HASH = 5;
    public static final int SHT_DYNAMIC = 6;
    public static final int SHT_NOTE = 7;
    public static final int SHT_NOBITS = 8;
    public static final int SHT_REL = 9;
    public static final int SHT_SHLIB = 10;
    public static final int SHT_DYNSYM = 11;
    public static final int SHT_LOPROC = 0x70000000;
    public static final int SHT_HIPROC = 0x7fffffff;
    public static final int SHT_LOUSER = 0x80000000;
    public static final int SHT_HIUSER = 0xffffffff;

    public static final int SHF_WRITE = 1;
    public static final int SHF_ALLOC = 2;
    public static final int SHF_EXECINSTR = 4;
    public static final int SHF_MASKPROC = 0xf0000000;

    public static byte[] ElfMagic = { (byte)0x7F, 'E', 'L', 'F' };



}
