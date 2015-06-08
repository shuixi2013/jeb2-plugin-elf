package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class SectionHeaderTable extends StreamReader {

    private short shentsize;
    private int shoff;
    private short shstrndx;
    private short shnum;

    private List<SectionHeader> entries = new ArrayList<>();


    public SectionHeaderTable(byte[] data, int shoff, short shentsize, short shnum, short shstrndx) {

        this.shoff = shoff;
        this.shentsize = shentsize;
        this.shnum = shnum;
        this.shstrndx = shstrndx;
        ByteArrayInputStream stream = new ByteArrayInputStream(data);

        stream.skip(shoff);

        stream.mark(0);

        // Get the string name table section information
        stream.skip(shstrndx * shentsize);
        
        NameSectionHeader names = new NameSectionHeader(data, shentsize, shoff + shstrndx * shentsize);

        StringTableSection nameTable = null;
        SectionHeader header;
        // First pass
        for(int sh_index=0; sh_index < shnum; sh_index++) {
            header = new SectionHeader(data, shentsize, shoff + sh_index * shentsize, names);
            entries.add(header);
            if(header.getType() == ELF.SHT_STRTAB) {
                if(header.getName().equals(".dynstr") || header.getName().equals(".strtab")) {
                    nameTable = (StringTableSection)header.getSection();
                }
            }
        }
        // Second pass
        for(int index=0; index < entries.size(); index++) {
            header = entries.get(index);
            if(header.getType() == ELF.SHT_DYNSYM || header.getType() == ELF.SHT_SYMTAB) {
                header.setNameTable(nameTable);
            }
        }
    }

    public SectionHeader getHeader(int index) {
         return entries.get(index);
    }

    /**
     * @return the shentsize
     */
    public short getEntsize() {
        return shentsize;
    }

    /**
     * @return the shoff
     */
    public int getOff() {
        return shoff;
    }

    /**
     * @return the shstrndx
     */
    public short getStrndx() {
        return shstrndx;
    }

    /**
     * @return the shnum
     */
    public short getNum() {
        return shnum;
    }

    public StringTableSection getStringTable() {
        for(SectionHeader entry : entries) {
            if(entry.getName().equals(".strtab")) {
                return (StringTableSection)(entry.getSection());
            }
        }
        throw new RuntimeException("No string tables found");
    }
    public List<Section> getSections() {
        List<Section> output = new ArrayList<>();
        for(SectionHeader entry : entries) {
            output.add(entry.getSection());
        }
        return output;
    }

    /**
     * @return the entries
     */
    public List<SectionHeader> getHeaders() {
        return entries;
    }

}
