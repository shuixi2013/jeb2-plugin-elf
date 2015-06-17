package com.pnf.ELF;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class SectionHeaderTable extends StreamReader {

    private short entrySize;
    private int offset;
    private short nameTableIndex;
    private short number;

    private List<SectionHeader> entries = new ArrayList<>();

    private List<SectionHeader> relocations = new ArrayList<>();


    public SectionHeaderTable(byte[] data, int offset, short entrySize, short number, short nameTableIndex) {

        this.offset = offset;
        this.entrySize = entrySize;
        this.number = number;
        this.nameTableIndex = nameTableIndex;
        ByteArrayInputStream stream = new ByteArrayInputStream(data);

        stream.skip(offset);

        stream.mark(0);

        // Get the string name table section information
        stream.skip(nameTableIndex * entrySize);
        
        NameSectionHeader names = new NameSectionHeader(data, entrySize, offset + nameTableIndex * entrySize);

        StringTableSection nameTable = null;
        SectionHeader header;
        // First pass to grab the name table and initialize sections
        for(int sh_index=0; sh_index < number; sh_index++) {
            header = new SectionHeader(data, entrySize, offset + sh_index * entrySize, names);
            entries.add(header);
            if(header.getType() == ELF.SHT_RELA || header.getType() == ELF.SHT_REL) {
                relocations.add(header);
            }
            if(header.getType() == ELF.SHT_STRTAB) {
                if(header.getName().equals(".dynstr") || header.getName().equals(".strtab")) {
                    nameTable = (StringTableSection)header.getSection();
                }
            }
        }
        // Second pass to set the name table of all sections
        for(int index=0; index < entries.size(); index++) {
            header = entries.get(index);
            if(header.getType() == ELF.SHT_DYNSYM || header.getType() == ELF.SHT_SYMTAB) {
                header.setNameTable(nameTable);
            }
        }
    }
    public void doRelocations(byte[] mem, int addrShift) {

        // Third pass to apply relocations
        RelocationSection section;
        SectionHeader symbolTable = null;
        offset = 0;
        int addend;
        char type;
        for(SectionHeader sym : entries) {
            if(sym.getType() == ELF.SHT_DYNSYM || sym.getType() == ELF.SHT_SYMTAB) {
                symbolTable = sym;
                break;
            }
        }
        if(symbolTable == null) 
            throw new RuntimeException("No symbol table for relocations");
        for(SectionHeader reloc : relocations) {
            section = (RelocationSection)(reloc.getSection());
            for(RelocationSectionEntry entry : section.getEntries()) {
                entry.setSymbolTable(symbolTable);
                entry.doRelocation(mem, addrShift);
            }
        }
    }

    public SectionHeader getHeader(int index) {
         return entries.get(index);
    }

    public short getEntrySize() {
        return entrySize;
    }


    public int getOffset() {
        return offset;
    }

    public short getNameTableIndex() {
        return nameTableIndex;
    }


    public short getNumber() {
        return number;
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

    public List<SectionHeader> getHeaders() {
        return entries;
    }

}
