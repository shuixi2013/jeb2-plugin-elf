package com.pnf.ELF;

import java.util.List;

public class ELFFile {
	// Wrapper for ELF Files
	
    private Header header;

    private int headerNameStringTable;


    public int getHeaderNameStringTable() {
		return headerNameStringTable;
	}

	private SectionHeaderTable sectionHeaderTable;
    private ProgramHeaderTable programHeaderTable;
    private List<Section> sections;
    public ELFFile(byte[] data) {
        header = new Header(data);

        sectionHeaderTable = new SectionHeaderTable(data, header.getEShoff(), header.getEShentSize(), header.getEShnum(), header.getEShstrndx());

        sections = sectionHeaderTable.getSections();


        programHeaderTable = new ProgramHeaderTable(data, header.getEPhoff(), header.getEPhentSize(), header.getEPhnum());

    }

    public Header getHeader() {
        return header;
    }

    public SectionHeaderTable getSectionHeaderTable() {
        return sectionHeaderTable;
    }

    public List<Section> getSections() {
        return sections;
    }
}
