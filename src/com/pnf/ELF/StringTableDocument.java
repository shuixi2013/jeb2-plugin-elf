package com.pnf.ELF;

import java.util.ArrayList;
import java.util.List;

import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;

public class StringTableDocument extends JebEventSource implements ITableDocument {

    SectionHeader header;

    List<TableRow> rows;

    public StringTableDocument(SectionHeader header) {
        this.header = header;

        rows = new ArrayList<>();
        String[] entries = ((StringTableSection)header.getSection()).getEntries();

        for(int index=0; index < entries.length; index++) {
            rows.add(new TableRow(new Cell(entries[index])));
        }
    }

    @Override
    public List<String> getColumnLabels() {
        ArrayList<String> output = new ArrayList<>();
        output.add("String");
        return output;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public ITableDocumentPart getTable() {
        // Don't bother calculating the actual size
        return getTablePart(0, rows.size());
    }

    @Override
    public ITableDocumentPart getTablePart(int start, int count) {
        return new StringTableDocumentPart(start, rows);
    }
}
