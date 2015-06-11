package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.SectionHeader;
import com.pnf.ELF.SectionHeaderTable;
import com.pnf.ELF.StringTableSection;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class SectionHeaderTableDocument extends JebEventSource implements ITableDocument {
    private static final ILogger logger = GlobalLog.getLogger(StringTableDocument.class);

    SectionHeaderTable headerTable;

    List<TableRow> rows;

    public SectionHeaderTableDocument(SectionHeaderTable headerTable) {
        this.headerTable = headerTable;

        rows = new ArrayList<>();
        List<SectionHeader> headers = headerTable.getHeaders();
        List<Cell> cells;
        SectionHeader header;
        for(int index=0; index < headers.size(); index++) {
            cells = new ArrayList<>();
            header = headers.get(index);
            cells.add(new Cell("" + index));
            cells.add(new Cell("" + header.getName()));
            cells.add(new Cell("" + header.getType_s()));
            if(index == 0) {
                cells.add(new Cell(""));
                cells.add(new Cell(""));
                cells.add(new Cell(""));
            }
            else if(header.getSection() != null) {
                cells.add(new Cell(String.format("%h", header.getAddress())));
                cells.add(new Cell(String.format("%h", header.getSection().getOffset())));
                cells.add(new Cell(String.format("%h", header.getSection().getSize())));
            }
            else {
                cells.add(new Cell(""));
                cells.add(new Cell(""));
                cells.add(new Cell(""));
            }
            cells.add(new Cell("" + header.getFlags()));
            cells.add(new Cell("" + header.getLink()));
            cells.add(new Cell("" + header.getInfo()));
            cells.add(new Cell("" + header.getAddressAlign()));
            rows.add(new TableRow(cells));
        }
    }

    @Override
    public List<String> getColumnLabels() {
        ArrayList<String> output = new ArrayList<>();
        output.add("Index");
        output.add("Name");
        output.add("Type");
        output.add("Address");
        output.add("Offset");
        output.add("Size");
        output.add("Flags");
        output.add("Link");
        output.add("Info");
        output.add("Addr Align");
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
