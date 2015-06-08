package com.pnf.ELF; 

import java.io.ByteArrayInputStream;

public class NoteSectionEntry extends StreamReader {
    private int namesz;
    private int descsz;
    private String name = "";
    private String desc = "";

    public NoteSectionEntry(byte[] data, int se_size, int se_offset) {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        stream.skip(se_offset);

        namesz = readInt(stream);
        descsz = readInt(stream);

        byte[] nameBytes = new byte[namesz];
        stream.read(nameBytes, 0, namesz);
        name = new String(nameBytes);

        // Skip the padding bytes, aligned to 4 byte words
        stream.skip(4 - namesz % 4);

        if(descsz > 0) {
            byte[] descBytes = new byte[descsz];
            stream.read(descBytes, 0, descsz);
            desc = new String(descBytes);
        }

    }

    public String getName() {
         return name;
    }
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return String.format("-- %s -- \n%s", name, desc);
    }
}
