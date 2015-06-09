package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;


public class NoteSection extends Section {
    private int entrySize;
    private List<NoteSectionEntry> entries = new ArrayList<>();

    public NoteSection(byte[] data, int size, int offset, int entrySize) {
        super(data, size, offset);

        for(int index=0; index < size / entrySize; index++) {
            entries.add(new NoteSectionEntry(data, entrySize, offset + entrySize * index));
        }
    }

    public List<NoteSectionEntry> getEntries() {
         return entries; 
    }

    public String getText() {
        String output = "";

        for(NoteSectionEntry entry : getEntries()) {
            output = output + entry.toString() + "\n";
        }
        return output;
    }
}
