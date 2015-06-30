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

    public byte[] image;

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

        image = new byte[maxAddr + maxAddrSize];


        for(SectionHeader header : sectionHeaderTable.getHeaders()) {
            // Address of 0 indicates it is not in the memory image
            if(header.getAddress() != 0 && header.getType() != ELF.SHT_NOBITS) {
                System.arraycopy(data, header.getOffset(), image, header.getAddress(), header.getSize());
            }
        }
        sectionHeaderTable.doRelocations(image);
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
    public boolean isLittleEndian() {
        return header.getEIClass() == ELF.ELFDATA2LSB;
    }
    public long getEntryPoint() {
        return header.getEntryPoint();
    }
    public byte[] getImage() {
        return image;
    }
    public long getImageSize() {
        return image.length;
    }
    public long getImageBase() {
        // Depends on page size
        return 0;
    }
    public int getWordSize() {
        switch(header.getData()) {
            case ELF.ELFCLASS32:
                return 32;
            case ELF.ELFCLASS64:
                return 64;
            default:
                // Means we don't know
                // Need to ask the user
                return -1;
        }
    }
    public int getFlags() {
        return header.getFlags();
    }
    public int getType() {
        return header.getType();

    }
}
