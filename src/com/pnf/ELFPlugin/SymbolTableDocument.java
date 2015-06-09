package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.SectionHeader;
import com.pnf.ELF.SymbolTableEntry;
import com.pnf.ELF.SymbolTableSection;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class SymbolTableDocument extends JebEventSource implements ITableDocument {
    private static final ILogger logger = GlobalLog.getLogger(SymbolTableEntry.class);

    SectionHeader header;
    SymbolTableSection section;

    List<TableRow> rows;

    public SymbolTableDocument(SectionHeader header) {
        this.header = header;

        rows = new ArrayList<>();

        section = (SymbolTableSection)(header.getSection());

        List<Cell> cells;
        List<SymbolTableEntry> entries = section.getEntries();
        SymbolTableEntry entry;
        for(int index=0; index < entries.size(); index++) {
            cells = new ArrayList<>();
            entry = entries.get(index);
            cells.add(new Cell("" + entry.getName()));
            cells.add(new Cell(String.format("%h", entry.getValue())));
            cells.add(new Cell(String.format("%h", entry.getSize())));
            cells.add(new Cell("" + entry.getTypeString()));
            cells.add(new Cell("" + entry.getBindString()));
            cells.add(new Cell("" + entry.getSectionHeaderIndex()));
            rows.add(new TableRow(cells));
        }
    }

    @Override
    public List<String> getColumnLabels() {
        ArrayList<String> output = new ArrayList<>();
        output.add("Name");
        output.add("Value");
        output.add("Size");
        output.add("Type");
        output.add("Bind");
        output.add("Index");
        return output;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public ITableDocumentPart getTable() {
        return getTablePart(0, rows.size());
    }

    @Override
    public ITableDocumentPart getTablePart(int start, int count) {
        return new TableDocumentPart(start, rows.subList(start, start+count));
    }



}
