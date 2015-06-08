package com.pnf.ELF;

import java.io.ByteArrayInputStream;

public class SymbolTableEntry extends StreamReader {
    protected ByteArrayInputStream stream;

    protected int st_name;
    protected String st_name_s;
    protected int st_value;
    protected int st_size;
    protected int st_info;
    protected int st_other;
    protected short st_shndex;
    protected int st_bind;
    protected String st_bind_s;
    protected int st_type;
    protected String st_type_s;

    public SymbolTableEntry(byte[] data, int offset, StringTableSection names) {
        stream = new ByteArrayInputStream(data);
        stream.skip(offset);

        st_name = readInt(stream);
        st_name_s = names.getString(st_name);

        st_value = readInt(stream);
        st_size = readInt(stream);
        st_info = readInt(stream);
        st_other = readInt(stream);
        st_shndex = readShort(stream);
        st_bind = st_info >> 4;
        switch(st_bind) {
            case 0:
                st_bind_s = "STB_LOCAL";
                break;
            case 1:
                st_bind_s = "STB_GLOBAL";
                break;
            case 2:
                st_bind_s = "STB_WEAK";
                break;
            case 13:
                st_bind_s = "STB_LOPROC";
                break;
            case 15:
                st_bind_s = "STB_HIPROC";
                break;
            default:
                st_bind_s = "UNKNOWN";
        }
        st_type = st_info & 0xf;
        switch(st_type) {
            case 0:
                st_type_s = "STT_NOTYPE";
                break;
            case 1:
                st_type_s = "STT_OBJECT";
                break;
            case 2:
                st_type_s = "STT_FUNC";
                break;
            case 3:
                st_type_s = "STT_SECTION";
                break;
            case 4:
                st_type_s = "STT_FILE";
                break;
            case 13:
                st_type_s = "STT_LOPROC";
                break;
            case 15:
                st_type_s = "STT_HIPROC";
                break;
            default:
                st_type_s = "UNKNOWN";
        }
    } 
    public String getName() {
         return st_name_s;
    }

    /**
     * @return the st_value
     */
    public int getValue() {
        return st_value;
    }

    /**
     * @return the st_size
     */
    public int getSize() {
        return st_size;
    }

    /**
     * @return the st_info
     */
    public int getInfo() {
        return st_info;
    }

    /**
     * @return the st_other
     */
    public int getOther() {
        return st_other;
    }

    /**
     * @return the st_shndex
     */
    public short getShndex() {
        return st_shndex;
    }

    /**
     * @return the st_bind_s
     */
    public String getBind_s() {
        return st_bind_s;
    }

    /**
     * @return the st_type_s
     */
    public String getType_s() {
        return st_type_s;
    }
}
