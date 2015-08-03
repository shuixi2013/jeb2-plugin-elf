package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.ProgramHeader;
import com.pnf.ELF.ProgramHeaderTable;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ICellCoordinates;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ProgramHeaderTableDocument extends JebEventSource implements
		ITableDocument {
	private static final ILogger logger = GlobalLog
			.getLogger(StringTableDocument.class);

	ProgramHeaderTable headerTable;

	List<TableRow> rows;

	public ProgramHeaderTableDocument(ProgramHeaderTable headerTable) {
		this.headerTable = headerTable;

		rows = new ArrayList<>();
		List<ProgramHeader> headers = headerTable.getHeaders();
		List<Cell> cells;
		ProgramHeader header;
		for (int index = 0; index < headers.size(); index++) {
			cells = new ArrayList<>();
			header = headers.get(index);
			cells.add(new Cell("" + index));
			cells.add(new Cell(header.getTypeString()));
			cells.add(new Cell(String.format("%x", header.getOffsetInFile())));
			cells.add(new Cell(String.format("%x", header.getVirtualAddress())));
			cells.add(new Cell(String.format("%x", header.getPhysicalAddress())));
			cells.add(new Cell(String.format("%x", header.getSizeInMemory())));
			cells.add(new Cell(String.format("%x", header.getSizeInFile())));
			cells.add(new Cell(String.format("%s", header.getFlagsString())));
			cells.add(new Cell(String.format("%x", header.getAlign())));
			rows.add(new TableRow(cells));
		}
	}

	@Override
	public List<String> getColumnLabels() {
		ArrayList<String> output = new ArrayList<>();
		output.add("Index");
		output.add("Type");
		output.add("Offset");
		output.add("Virtual Address");
		output.add("Physical Address");
		output.add("Memory Size");
		output.add("File Size");
		output.add("Flags");
		output.add("Align");
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
		return new TableDocumentPart(start, rows.subList(start, start + count));
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
