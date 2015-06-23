package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class SectionHeader extends StreamReader {

    protected int s_name;
    protected String s_name_s;
    protected int s_type;
    protected String s_type_s;
    protected int s_flags;
    protected List<String> s_flags_s = new ArrayList<>();
    protected int s_addr;
    protected int s_offset;
    protected int s_size;
    protected int s_link;
    protected int s_info;
    protected int s_addralign;
    protected int s_entsize;
    protected Section section;
    protected StringTableSection nameTable;



    // data - whole elf file
    // size - size of header
    // offset - start of header in elf file
    public SectionHeader(byte[] data, int size, int offset, NameSectionHeader names) {
        this(data, size, offset, names.getSectionName(readInt(new ByteArrayInputStream(data), offset)));
    }
    public SectionHeader(byte[] data, int size, int offset, String name) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(offset);
        s_name = readInt(stream);
        s_type = readInt(stream);
        s_name_s = name;

        s_flags = readInt(stream);
        if((s_flags & 0x1) == ELF.SHF_WRITE)
            s_flags_s.add("SHF_WRITE");
        if((s_flags & 0x2) == ELF.SHF_ALLOC)
            s_flags_s.add("SHF_ALLOC");
        if((s_flags & 0x4) == ELF.SHF_EXECINSTR)
            s_flags_s.add("SHF_EXECINSTR");
        if((s_flags & 0xf0000000) == ELF.SHF_MASKPROC)
            s_flags_s.add("SHF_MASKPROC");
        s_addr = readInt(stream);
        s_offset = readInt(stream);
        s_size = readInt(stream);
        s_link = readInt(stream);
        s_info = readInt(stream);
        s_addralign = readInt(stream);
        s_entsize = readInt(stream);

        switch(s_type) {
            case ELF.SHT_NULL:
                section = null;
                break;
            case ELF.SHT_SYMTAB:
                section = new SymbolTableSection(data, s_size, s_offset, s_entsize, this.nameTable);
                break;
            case ELF.SHT_STRTAB:
                section = new StringTableSection(data, s_size, s_offset);
                break;
            case ELF.SHT_RELA:
                section = new RelocationSection(data, s_size, s_offset, s_entsize, true);
                break;
            case ELF.SHT_REL:
                section = new RelocationSection(data, s_size, s_offset, s_entsize, false);
                break;
            case ELF.SHT_HASH:
                section = new HashTableSection(data, s_size, s_offset);
                break;
            case ELF.SHT_NOTE:
                section = new NoteSection(data, s_size, s_offset, s_entsize);
                break;
            case ELF.SHT_NOBITS:
                section = null;
                break;
            case ELF.SHT_DYNSYM:
                section = new SymbolTableSection(data, s_size, s_offset, s_entsize, this.nameTable);
                break;
            case ELF.SHT_DYNAMIC:
                section = new DynamicSection(data, s_size, s_offset, s_entsize);
                break;
            case ELF.SHT_PROGBITS:
            case ELF.SHT_SHLIB:
            case ELF.SHT_LOPROC:
            case ELF.SHT_HIPROC:
            case ELF.SHT_LOUSER:
            case ELF.SHT_HIUSER:
                section = new Section(data, s_size, s_offset);
                break;
            default:
                section = new Section(data, s_size, s_offset);
        }
        s_type_s = ELF.getSectionTypeString(s_type);
    }

    /**
     * @return the s_name_s
     */
    public String getName() {
        return s_name_s;
    }

    /**
     * @return the s_type
     */
    public int getType() {
        return s_type;
    }

    public String getType_s() {
        return s_type_s;
    }

    public Section getSection() {
         return section;
    }
    public int getOffset() {
        return s_offset;
    }
    public int getSize() {
        return s_size;
    }
    
    public int getAddress() {
        return s_addr;
    }
    public int getLink() {
        return s_link;
    }
    public int getInfo() {
        return s_info;
    }
    public int getAddressAlign() {
        return s_addralign;
    }

    public int getFlags() {
        return s_flags;
    }

    public String getFlagsString() {
        String output = "";
        for(String flag : s_flags_s) {
            //output += flag.replace("SHF_", "").charAt(0);
            output += flag + "\n";
        }
        return output;
    }


    public void setNameTable(StringTableSection nameTable) {
        this.nameTable = nameTable;
        this.section.setNameTable(nameTable);
    }
}

