package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class SymbolTableEntry extends StreamReader {
	protected ByteArrayInputStream stream;

	protected int name;
	protected String nameString;
	protected int value;
	protected int size;
	protected int info;
	protected int other;
	protected short sectionHeaderIndex;
	protected int bind;
	protected String bindString;
	protected int type;
	protected String typeString;

	public SymbolTableEntry(byte[] data, int offset) {
		stream = new ByteArrayInputStream(data);
		stream.skip(offset);

		name = readInt(stream);

		value = readInt(stream);
		size = readInt(stream);
		info = stream.read();
		other = stream.read();
		sectionHeaderIndex = readShort(stream);
		bind = info >> 4;
		bindString = ELF.getSTBString(bind);

		type = info & 0xf;
		typeString = ELF.getSTTString(type);
	}

	public void setName(StringTableSection nameTable) {
		this.nameString = nameTable.getString(name);
	}

	public String getName() {
		return nameString;
	}

	public int getValue() {
		return value;
	}

	public int getSize() {
		return size;
	}

	public int getInfo() {
		return info;
	}

	public int getOther() {
		return other;
	}

	public short getSectionHeaderIndex() {
		return sectionHeaderIndex;
	}

	public String getBindString() {
		return bindString;
	}

	public int getBind() {
		return bind;
	}

	public int getType() {
		return type;
	}

	public String getTypeString() {
		return typeString;
	}
}
