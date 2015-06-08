package com.pnf.ELF;
public class StringTableSection extends Section {

    public StringTableSection(byte[] data, int sh_size, int sh_offset) {
        super(data, sh_size, sh_offset);
    }

    public String getString(int index) {
        return getStringFromTable(stream, index);
    }

    public String getText() {
        return new String(getBytes());
    }
    public String[] getEntries() {
        return getText().split("[" + (char)0 + "]");
    }
}
