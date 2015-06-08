package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Header extends StreamReader {

    private int start;
    private int end;
    private int e_nident; // size of identification struct
    private byte ei_mag0;
    private byte ei_mag1;
    private byte ei_mag2;
    private byte ei_mag3;
    private byte ei_class;
    private String ei_class_s;
    private byte ei_data;
    private String ei_data_s;
    private byte ei_version;
    private String ei_version_s;
    private byte ei_osabi;
    private String ei_osabi_s;
    private byte ei_abiversion;
    private String ei_abiversion_s;

    private short e_type;
    private String e_type_s;
    private short e_machine;
    private String e_machine_s;
    private int e_version;
    private String e_version_s;
    private int e_entry;
    private int e_phoff;
    private int e_shoff;
    private int e_flags;
    private short e_ehsize;
    private short e_phentsize;
    private short e_phnum;
    private short e_shentsize;
    private short e_shnum;
    private short e_shstrndx;

    private int nameSectionHeaderStart;
    private int nameSectionStart;


    // ELF Header
    // Bytes: name
    // 0-3  : ei_mag0 - ei_mag3
    // 4    : ei_class
    // 5    : ei_data
    // 6    : ei_version
    // 7    : ei_osabi
    // 8-15 : ei_pad
    // 16-17: e_type
    // 18-19: e_machine
    // 20-23: e_version
    // 24-27: e_entry
    // 28-31: e_phoff
    // 32-35: e_shoff
    // 36-39: e_flags
    // 40-41: e_ehsize (size of ELF header in bytes)
    // 42-43: e_phentsize (size of program file header entry) *
    // 44-45: e_phnum (number of program header entries) *
    // 46-47: e_shentsize (size of section header table entry) *
    // 48-49: e_shnum (number of section header entries) *
    // 50-51: e_shstrndx (location of string table for section header table)
    public Header(byte[] data) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        /******* Read Ident Struct ******/
        ei_mag0 = (byte)stream.read();
        ei_mag1 = (byte)stream.read();
        ei_mag2 = (byte)stream.read();
        ei_mag3 = (byte)stream.read();
        if(!checkBytes(new byte[] {ei_mag0, ei_mag1, ei_mag2, ei_mag3}, 0, ELF.ElfMagic))
            throw new IllegalArgumentException("Magic number does not match");
        ei_class = (byte)stream.read();
        switch(ei_class) {
            case ELF.ELFCLASSNONE:
                ei_class_s = "ELFCLASSNONE";
                break;
            case ELF.ELFCLASS32:
                ei_class_s = "ELFCLASS32";
                break;
            case ELF.ELFCLASS64:
                ei_class_s = "ELFCLASS64";
                break;
            default:
                ei_class_s = "UNKNOWN";
        }
        ei_data = (byte)stream.read();
        switch(ei_data) {
            case ELF.ELFDATANONE:
                ei_data_s = "ELFDATANONE";
                break;
            case ELF.ELFDATA2LSB:
                ei_data_s = "ELFDATA2LSB";
                break;
            case ELF.ELFDATA2MSB:
                ei_data_s = "ELFDATA2MSB";
                break;
            default:
                ei_data_s = "UNKNOWN";
        }
        ei_version = (byte)stream.read();
        switch(ei_version) {
            case ELF.EV_NONE:
                ei_version_s = "EV_NONE";
                break;
            case ELF.EV_CURRENT:
                ei_version_s = "EV_CURRENT";
                break;
            default:
                ei_version_s = "UNKNOWN";
        }
        ei_osabi = (byte)stream.read();
        ei_abiversion = (byte)stream.read();
        stream.skip(7);
        /******* Done Ident Struct ******/

        /******* Read Header ******/
        e_type = readShort(stream);
        switch(e_type) {
            case ELF.ET_NONE:
                e_type_s = "ET_NONE";
                break;
            case ELF.ET_REL:
                e_type_s = "ET_REL";
                break;
            case ELF.ET_EXEC:
                e_type_s = "ET_EXEC";
                break;
            case ELF.ET_DYN:
                e_type_s = "ET_DYN";
                break;
            case ELF.ET_CORE:
                e_type_s = "ET_CORE";
                break;
            case ELF.ET_LOPROC:
                e_type_s = "ET_LOPROC";
                break;
            case ELF.ET_HIPROC:
                e_type_s = "ET_HIPROC";
                break;
            default:
                e_type_s = "UNKNOWN";
        }
        e_machine = readShort(stream);
        switch(e_machine) {
            case ELF.EM_NONE: 
                e_machine_s = "EM_NONE";
                break;
            case ELF.EM_M32:
                e_machine_s = "EM_M32";
                break;
            case ELF.EM_SPARC:
                e_machine_s = "EM_SPARC";
                break;
            case ELF.EM_386:
                e_machine_s = "EM_386";
                break;
            case ELF.EM_68K:
                e_machine_s = "EM_68K";
                break;
            case ELF.EM_88K:
                e_machine_s = "EM_88K";
                break;
            case ELF.EM_860:
                e_machine_s = "EM_860";
                break;
            case ELF.EM_MIPS:
                e_machine_s = "EM_MIPS";
                break;
            default:
                e_machine_s = "UNKNOWN";
        }
        e_version = readInt(stream);
        switch(e_version) {
            case ELF.EV_NONE:
                e_version_s = "EV_NONE";
                break;
            case ELF.EV_CURRENT:
                e_version_s = "EV_CURRENT";
                break;
            default:
                e_version_s = "UNKNOWN";
        }
        e_entry = readInt(stream);
        e_phoff = readInt(stream);
        e_shoff = readInt(stream);
        e_flags = readInt(stream);
        e_ehsize = readShort(stream);
        e_phentsize = readShort(stream);
        e_phnum = readShort(stream);
        e_shentsize = readShort(stream);
        e_shnum = readShort(stream);
        e_shstrndx = readShort(stream);
        /******* Done header ******/

    }
    public int getStart() {
        return this.start;
    }
    public int getEnd() {
        return this.end;
    }

    /**
     * @return the e_nident
     */
    public int getNident() {
        return e_nident;
    }

    /**
     * @return the ei_class
     */
    public byte getEiClass() {
        return ei_class;
    }

    /**
     * @return the ei_class_s
     */
    public String getClass_s() {
        return ei_class_s;
    }

    /**
     * @return the ei_data
     */
    public byte getData() {
        return ei_data;
    }

    /**
     * @return the ei_data_s
     */
    public String getData_s() {
        return ei_data_s;
    }

    /**
     * @return the ei_version
     */
    public byte getEiVersion() {
        return ei_version;
    }

    /**
     * @return the ei_version_s
     */
    public String getEiVersion_s() {
        return ei_version_s;
    }

    /**
     * @return the e_type
     */
    public short getType() {
        return e_type;
    }

    /**
     * @return the e_type_s
     */
    public String getType_s() {
        return e_type_s;
    }

    /**
     * @return the e_machine
     */
    public short getMachine() {
        return e_machine;
    }

    /**
     * @return the e_machine_s
     */
    public String getMachine_s() {
        return e_machine_s;
    }

    /**
     * @return the e_version
     */
    public int getVersion() {
        return e_version;
    }

    /**
     * @return the e_version_s
     */
    public String getVersion_s() {
        return e_version_s;
    }

    /**
     * @return the e_entry
     */
    public int getEntry() {
        return e_entry;
    }

    /**
     * @return the e_phoff
     */
    public int getPhoff() {
        return e_phoff;
    }

    /**
     * @return the e_shoff
     */
    public int getShoff() {
        return e_shoff;
    }

    /**
     * @return the e_flags
     */
    public int getFlags() {
        return e_flags;
    }

    /**
     * @return the e_ehsize
     */
    public short getEhsize() {
        return e_ehsize;
    }

    /**
     * @return the e_phentsize
     */
    public short getPhentsize() {
        return e_phentsize;
    }

    /**
     * @return the e_phnum
     */
    public short getPhnum() {
        return e_phnum;
    }

    /**
     * @return the e_shentsize
     */
    public short getShentsize() {
        return e_shentsize;
    }

    /**
     * @return the e_shnum
     */
    public short getShnum() {
        return e_shnum;
    }

    /**
     * @return the e_shstrndx
     */
    public short getShstrndx() {
        return e_shstrndx;
    }

    @Override
    public String toString() {
        return "ELF File " +
            e_type_s + " " +
            e_machine_s + " " + 
            e_version_s + "\n\t" + 
            e_shnum + " sections";
    }

}
