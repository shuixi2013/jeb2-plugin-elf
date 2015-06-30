package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.SectionHeader;
import com.pnf.ELF.StringTableSection;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ICellCoordinates;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class StringTableDocument extends JebEventSource implements ITableDocument {
    private static final ILogger logger = GlobalLog.getLogger(StringTableDocument.class);

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
        return new TableDocumentPart(start, rows.subList(start, start+count));
    }


    @Override
    public ICellCoordinates addressToCoordinates(String address) {
        return null;
    }

    @Override
    public String coordinatesToAddress(ICellCoordinates coordinates) {
        return null;
    }

    @Override
    public void dispose() {
    }

}
