package com.pnf.ELF;

import java.io.ByteArrayInputStream;

import com.pnf.ELF.ELF.R_SYMBOL;

public class RelocationSectionEntry extends StreamReader {

	private int size;
	private int offset;

	private int entryOffset;
	private int info;
	private int addend;

	private R_SYMBOL symbol;
	private SymbolTableEntry symTabEntry;

	private int symbolTableIndex;
	private SectionHeader symbolTable;
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

	public int getSymbolTableIndex() {
		return symbolTableIndex;
	}

	public int getType() {
		return type;
	}

	public boolean isRELA() {
		return RELA;
	}

	private boolean RELA;

	public RelocationSectionEntry(byte[] data, int se_size, int se_offset,
			boolean RELA) {
		ByteArrayInputStream stream = new ByteArrayInputStream(data);
		stream.skip(se_offset);
		this.size = se_size;
		this.offset = se_offset;
		entryOffset = readInt(stream);
		info = readInt(stream);
		// Not always set
		if (RELA) {
			addend = readInt(stream);
		}
		symbolTableIndex = info >> 8;
		type = (char) info;
	}

	public void setSymbolTable(SectionHeader symbolTable) {
		this.symbolTable = symbolTable;

		symTabEntry = ((SymbolTableSection) (symbolTable.getSection()))
				.getEntry(symbolTableIndex);
	}

	public void doRelocation(byte[] mem) {
		/*
		 * int A; if(RELA) { A = addend; } else { logger.info("%d", mem.length);
		 * if(entryOffset >= mem.length) {
		 * 
		 * } A = mem[entryOffset]; }
		 * 
		 * R_SYMBOL symbol = R_SYMBOL.EXTERNAL; if(symTabEntry.getBind() ==
		 * ELF.STB_LOCAL && symTabEntry.getType() == ELF.STT_SECTION) { symbol =
		 * R_SYMBOL.LOCAL; } int newVal; byte[] newBytes; switch(type) { case
		 * ELF.R_MIPS_NONE: return; case ELF.R_MIPS_REL_32: if(symbol ==
		 * R_SYMBOL.LOCAL) { newVal = ELF.relocate(type, A, 32, 0, 0,
		 * symTabEntry.getValue(), 0, 0, 0, 0, 0, symbol); newBytes =
		 * ByteBuffer.allocate(4).putInt(newVal).array();
		 * System.arraycopy(newBytes, 0, mem, entryOffset, 4); } else if(symbol
		 * == R_SYMBOL.EXTERNAL) { newVal = ELF.relocate(type, A, 32, 0, 0,
		 * symTabEntry.getValue(), 0,0,0,0,0, symbol); newBytes =
		 * ByteBuffer.allocate(4).putInt(newVal).array();
		 * System.arraycopy(newBytes, 0, mem, entryOffset, 4); } return; }
		 */
	}

	public String getSymbolName() {
		return symTabEntry.getName();
	}
}
