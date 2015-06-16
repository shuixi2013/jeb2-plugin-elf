package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class Header extends StreamReader {

    private int start;
    private int end;
    private int identSize; // size of identification struct
    private byte eiMag0;
    private byte eiMag1;
    private byte eiMag2;
    private byte eiMag3;
    private byte eiClass;
    private String eiClassString;
    private byte eiData;
    private String eiDataString;
    private byte eiVersion;
    private String eiVersionString;
    private byte eiOsabi;
    private String eiOsabiString;
    private byte eiAbiversion;
    private String eiAbiversionString;

    private short eType;
    private String eTypeString;
    private short eMachine;
    private String eMachineString;
    private int eVersion;
    private String eVersionString;
    private int eEntry;
    private int ePhoff;
    private int eShoff;
    private int eFlags;
    private short eEhsize;
    private short ePhentSize;
    private short ePhnum;
    private short eShentSize;
    private short eShnum;
    private short eShstrndx;

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
        eiMag0 = (byte)stream.read();
        eiMag1 = (byte)stream.read();
        eiMag2 = (byte)stream.read();
        eiMag3 = (byte)stream.read();

        if(!checkBytes(new byte[] {eiMag0, eiMag1, eiMag2, eiMag3}, 0, ELF.ElfMagic))
            throw new IllegalArgumentException("Magic number does not match");

        eiClass = (byte)stream.read();
        eiClassString = ELF.getClassString(eiClass);

        eiData = (byte)stream.read();
        eiDataString = ELF.getDataString(eiData);

        eiVersion = (byte)stream.read();
        eiVersionString = ELF.getVersionString(0);

        eiOsabi = (byte)stream.read();
        eiAbiversion = (byte)stream.read();

        stream.skip(7);
        /******* Done Ident Struct ******/

        /******* Read Header ******/
        eType = readShort(stream);
        eTypeString = ELF.getTypeString(eType);
        eMachine = readShort(stream);
        eMachineString = ELF.getMachineString(eMachine);
        eVersion = readInt(stream);
        eVersionString = ELF.getVersionString(eVersion);
        eEntry = readInt(stream);
        ePhoff = readInt(stream);
        eShoff = readInt(stream);
        eFlags = readInt(stream);
        eEhsize = readShort(stream);
        ePhentSize = readShort(stream);
        ePhnum = readShort(stream);
        eShentSize = readShort(stream);
        eShnum = readShort(stream);
        eShstrndx = readShort(stream);
        /******* Done header ******/

    }
    public int getStart() {
        return this.start;
    }
    public int getEnd() {
        return this.end;
    }


    public int getIdentSize() {
        return identSize;
    }


    public byte getEIClass() {
        return eiClass;
    }

    public String getClassString() {
        return eiClassString;
    }


    public byte getData() {
        return eiData;
    }


    public String getDataString() {
        return eiDataString;
    }


    public byte getEIVersion() {
        return eiVersion;
    }

    public String getEIVersionString() {
        return eiVersionString;
    }

    public short getType() {
        return eType;
    }

    public String getTypeString() {
        return eTypeString;
    }

    public short getMachine() {
        return eMachine;
    }

    public String getMachineString() {
        return eMachineString;
    }


    public int getEVersion() {
        return eVersion;
    }

    public String getEVersionString() {
        return eVersionString;
    }

    public int getEntryPoint() {
        return eEntry;
    }


    public int getPHOffset() {
        return ePhoff;
    }

    public int getShoff() {
        return eShoff;
    }


    public int getFlags() {
        return eFlags;
    }

    public short getHeaderSize() {
        return eEhsize;
    }

    public short getPHEntrySize() {
        return ePhentSize;
    }


    public short getPHNumber() {
        return ePhnum;
    }

    public short getSHEntrySize() {
        return eShentSize;
    }

    public short getSHNumber() {
        return eShnum;
    }

    public short getSHStringIndex() {
        return eShstrndx;
    }

    @Override
    public String toString() {
        return "ELF File " +
            eTypeString + " " +
            eMachineString + " " + 
            eVersionString + "\n\t" + 
            eShnum + " sections";
    }

}
