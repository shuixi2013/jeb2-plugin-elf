package com.pnf.ELF;

import java.io.ByteArrayInputStream;


public class RelocationSectionEntry extends StreamReader {

    private int size;
    private int offset;

    private int entryOffset;
    private int info;
    private int addend;

    private int symbol;
    private int type;
    public int getSize() {
		return size;
	}

	public int getOffset() {
		return offset;
	}

	public int getEntryOffset() {
		return entryOffset;
	}

	public int getInfo() {
		return info;
	}

	public int getAddend() {
		return addend;
	}

	public int getSymbol() {
		return symbol;
	}

	public int getType() {
		return type;
	}

	public boolean isRELA() {
		return RELA;
	}

	private boolean RELA;

    public RelocationSectionEntry(byte[] data, int se_size, int se_offset, boolean RELA) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(se_offset);
        this.size = se_size;
        this.offset = se_offset;
        entryOffset = readInt(stream);
        info = readInt(stream);
        // Not always set
        if(RELA) {
            addend = readInt(stream);
        }

        symbol = info >> 8;
        type = (char)info;
    }
}
