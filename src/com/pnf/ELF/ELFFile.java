package com.pnf.ELF;

import java.util.List;

public class ELFFile {
    private Header header;

    private int headerNameStringTable;


    private SectionHeaderTable sectionHeaderTable;
    private ProgramHeaderTable programHeaderTable;
    private List<Section> sections;
    public ELFFile(byte[] data) {
        header = new Header(data);

        sectionHeaderTable = new SectionHeaderTable(data, header.getE_shoff(), header.getE_shentsize(), header.getE_shnum(), header.getE_shstrndx());

        sections = sectionHeaderTable.getSections();


        programHeaderTable = new ProgramHeaderTable(data, header.getE_phoff(), header.getE_phentsize(), header.getE_phnum());

    }

    public Header getHeader() {
        return header;
    }

    /**
     * @return the sectionHeaderTable
     */
    public SectionHeaderTable getSectionHeaderTable() {
        return sectionHeaderTable;
    }

    /**
     * @return the sections
     */
    public List<Section> getSections() {
        return sections;
    }
}
