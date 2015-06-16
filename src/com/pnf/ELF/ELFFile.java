package com.pnf.ELF;

import java.io.FileOutputStream;
import java.util.List;

import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ELFFile {
    private static final ILogger logger = GlobalLog.getLogger(ELFFile.class);
	// Wrapper for ELF Files
	
    private Header header;

    private int headerNameStringTable;

    public byte[] memoryImage;

    public int getHeaderNameStringTable() {
		return headerNameStringTable;
	}

	private SectionHeaderTable sectionHeaderTable;
    private ProgramHeaderTable programHeaderTable;
    private List<Section> sections;
    public ELFFile(byte[] data) {
        header = new Header(data);

        sectionHeaderTable = new SectionHeaderTable(data, header.getShoff(), header.getSHEntrySize(), header.getSHNumber(), header.getSHStringIndex());

        sections = sectionHeaderTable.getSections();

        programHeaderTable = new ProgramHeaderTable(data, header.getPHOffset(), header.getPHEntrySize(), header.getPHNumber());

        int maxAddr=0;
        int maxAddrSize=0;
        int addr;
        int minAddr=Integer.MAX_VALUE;
        for(SectionHeader header : sectionHeaderTable.getHeaders()) {
            addr = header.getAddress();
            if(addr == 0) {
                continue;
            }
            if(addr > maxAddr) {
                maxAddr = addr;
                maxAddrSize = header.getSize();
            }
            if(addr < minAddr) {
                minAddr = addr;
            }
        }

        memoryImage = new byte[maxAddr + maxAddrSize - minAddr];


        for(SectionHeader header : sectionHeaderTable.getHeaders()) {
            // Address of 0 indicates it is not in the memory image
            if(header.getAddress() != 0) {
                System.arraycopy(data, header.getOffset(), memoryImage, header.getAddress()-minAddr, header.getSize());
            }
        }
    }

    private void applyRelocations() {
        for(SectionHeader header : sectionHeaderTable.getHeaders()) {
            if(header.getType() == ELF.SHT_RELA || header.getType() == ELF.SHT_REL) {

            }
        }

    }

    public byte[] getMem() {
        return memoryImage;
    }

    public Header getHeader() {
        return header;
    }

    public SectionHeaderTable getSectionHeaderTable() {
        return sectionHeaderTable;
    }

    public ProgramHeaderTable getProgramHeaderTable() {
        return programHeaderTable;
    }

    public List<Section> getSections() {
        return sections;
    }

    public int getArch() {
        return header.getMachine();
    }
}
