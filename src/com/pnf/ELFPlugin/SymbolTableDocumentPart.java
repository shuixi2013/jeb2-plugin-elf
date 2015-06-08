package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;


public class SymbolTableDocumentPart implements ITableDocumentPart {
    private ArrayList<TableRow> rows;
    private int rowIndex;

    public SymbolTableDocumentPart(int rowIndex, List<TableRow> rows) {
        this.rowIndex = rowIndex;
        this.rows = new ArrayList<>(rows);
    }

    public int getFirstRowIndex() {
        return rowIndex;
    }

    public List<TableRow> getRows() {
        return rows;
    }
}
