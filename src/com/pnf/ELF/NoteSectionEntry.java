package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class NoteSectionEntry extends StreamReader {
	private int nameSize;
	private int descSize;
	private int type;
	private int size;
	private String name = "";
	private String desc = "";

	public NoteSectionEntry(byte[] data, int offset) {
		ByteArrayInputStream stream = new ByteArrayInputStream(data);
		stream.skip(offset);

		nameSize = readInt(stream);
		descSize = readInt(stream);
		type = readInt(stream);

		byte[] nameBytes = new byte[nameSize];
		stream.read(nameBytes, 0, nameSize);
		name = new String(nameBytes);

		// Skip the padding bytes, aligned to 4 byte words
		stream.skip(4 - nameSize % 4);

		if (descSize > 0) {
			byte[] descBytes = new byte[descSize];
			stream.read(descBytes, 0, descSize);
			desc = new String(descBytes);
		}
		size = data.length - stream.available() - offset;

	}

	public int getSize() {
		return size;
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
