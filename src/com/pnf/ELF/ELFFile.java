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

        sectionHeaderTable = new SectionHeaderTable(data, header.getShoff(), header.getShentsize(), header.getShnum(), header.getShstrndx());

        sections = sectionHeaderTable.getSections();


        programHeaderTable = new ProgramHeaderTable(data, header.getPhoff(), header.getPhentsize(), header.getPhnum());

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
