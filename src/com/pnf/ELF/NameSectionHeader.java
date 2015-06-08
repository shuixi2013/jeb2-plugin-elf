package com.pnf.ELF;


public class NameSectionHeader extends SectionHeader {


    public NameSectionHeader(byte[] data, int size, int offset) {
        super(data, size, offset, ".shstrtab");
        logger.info("Created name section table");

    }
    public String getSectionName(int index) {
        logger.info("Getting string from %d", section.getOffset() + index);
        return ((StringTableSection)section).getString(index);
    }
    public int getTableOffset() {
        return section.getOffset();
    }
}
