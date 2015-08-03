package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class DynamicSectionEntry extends StreamReader {

	private int tag;
	private String tagString;
	private int un;
	private int val;
	private String valString;
	private int ptr;

	public int getTag() {
		return tag;
	}

	public String getTagString() {
		return tagString;
	}

	public int getUn() {
		return un;
	}

	public int getVal() {
		return val;
	}

	public String getValString() {
		return valString;
	}

	public int getPtr() {
		return ptr;
	}

	public DynamicSectionEntry(byte[] data, int size, int offset) {
		ByteArrayInputStream stream = new ByteArrayInputStream(data);
		stream.skip(offset);
		stream.mark(0);

		tag = readInt(stream);
		tagString = ELF.getDT(tag);
		un = readInt(stream);

		switch (tag) {
		case ELF.DT_NULL:
			val = un;
			break;
		case ELF.DT_NEEDED:
			val = un;
			break;
		case ELF.DT_PLTRELSZ:
			ptr = un;
			break;
		case ELF.DT_PLTGOT:
			ptr = un;
			break;
		case ELF.DT_HASH:
			ptr = un;
			break;
		case ELF.DT_STRTAB:
			ptr = un;
			break;
		case ELF.DT_SYMTAB:
			ptr = un;
			break;
		case ELF.DT_RELA:
			ptr = un;
			break;
		case ELF.DT_RELASZ:
			val = un;
			break;
		case ELF.DT_RELAENT:
			val = un;
			break;
		case ELF.DT_STRSZ:
			val = un;
			break;
		case ELF.DT_SYMENT:
			val = un;
			break;
		case ELF.DT_INIT:
			ptr = un;
			break;
		case ELF.DT_FINI:
			ptr = un;
			break;
		case ELF.DT_SONAME:
			val = un;
			break;
		case ELF.DT_RPATH:
			val = un;
			break;
		case ELF.DT_SYMBOLIC:
			break;
		case ELF.DT_REL:
			ptr = un;
			break;
		case ELF.DT_RELSZ:
			val = un;
			break;
		case ELF.DT_RELENT:
			val = un;
			break;
		case ELF.DT_PLTREL:
			val = un;
			break;
		case ELF.DT_DEBUG:
			ptr = un;
			break;
		case ELF.DT_TEXTREL:
			break;
		case ELF.DT_JMPREL:
			ptr = un;
			break;
		case ELF.DT_LOPROC:
			break;
		case ELF.DT_HIPROC:
			break;

		}
	}

	public void setStringTable(SectionHeader stringTable) {
		switch (tag) {
		case ELF.DT_NEEDED:
		case ELF.DT_SONAME:
		case ELF.DT_RPATH:
			if (stringTable.getSection() instanceof StringTableSection) {
				valString = ((StringTableSection) stringTable.getSection())
						.getString(val);
				break;
			}
			// Otherwise fall through to default
		default:
			valString = Integer.toHexString(un);
		}
	}
}
