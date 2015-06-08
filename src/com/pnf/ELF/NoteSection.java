package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;


public class NoteSection extends Section {
    private int s_entsize;
    private List<NoteSectionEntry> entries = new ArrayList<>();

    public NoteSection(byte[] data, int s_size, int s_offset, int s_entsize) {
        super(data, s_size, s_offset);

        for(int index=0; index < s_size / s_entsize; index++) {
            entries.add(new NoteSectionEntry(data, s_entsize, s_offset + s_entsize * index));
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
