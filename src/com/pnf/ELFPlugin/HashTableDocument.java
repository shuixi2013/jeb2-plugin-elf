package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.HashTableSection;
import com.pnf.ELF.SectionHeader;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;
import com.pnfsoftware.jeb.core.output.table.ICellCoordinates;

public class HashTableDocument extends JebEventSource implements ITableDocument {

    SectionHeader header;
    HashTableSection section;

    List<TableRow> rows;

    public HashTableDocument(SectionHeader header) {
        this.header = header;

        rows = new ArrayList<>();

        section = (HashTableSection)(header.getSection());
        

        rows.add(new TableRow(new Cell("Buckets")));
        for(int index=0; index < section.getNBuckets(); index++) {
            rows.add(new TableRow(new Cell("" + section.getBucket(index))));
        }
        rows.add(new TableRow(new Cell("Chains")));
        for(int index=0; index < section.getNChains(); index++) {
            rows.add(new TableRow(new Cell("" + section.getChain(index))));
        }
    }

    @Override
    public List<String> getColumnLabels() {
        ArrayList<String> output = new ArrayList<>();
        output.add("Value");
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
