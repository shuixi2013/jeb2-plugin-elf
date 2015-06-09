package com.pnf.ELF;


public class NameSectionHeader extends SectionHeader {


    public NameSectionHeader(byte[] data, int size, int offset) {
        super(data, size, offset, ".shstrtab");

    }
    public String getSectionName(int index) {
        return ((StringTableSection)section).getString(index);
    }
    public int getTableOffset() {
        return section.getOffset();
    }
}
