package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

public class NoteSection extends Section {
	private int entrySize;
	private List<NoteSectionEntry> entries = new ArrayList<>();

	public NoteSection(byte[] data, int size, int offset, int entrySize) {
		super(data, size, offset);

		int entryOffset = 0;
		int nameSize;
		int descSize;
		NoteSectionEntry entry;
		while (entryOffset < size) {
			entry = new NoteSectionEntry(data, entryOffset + offset);
			entries.add(entry);
			entryOffset += entry.getSize();
		}
	}

	public List<NoteSectionEntry> getEntries() {
		return entries;
	}

	public String getText() {
		String output = "";

		for (NoteSectionEntry entry : getEntries()) {
			output = output + entry.toString() + "\n";
		}
		return output;
	}
}
