package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.DynamicSection;
import com.pnf.ELF.DynamicSectionEntry;
import com.pnf.ELF.SectionHeader;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.table.ICellCoordinates;
import com.pnfsoftware.jeb.core.output.table.ITableDocument;
import com.pnfsoftware.jeb.core.output.table.ITableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.Cell;
import com.pnfsoftware.jeb.core.output.table.impl.TableDocumentPart;
import com.pnfsoftware.jeb.core.output.table.impl.TableRow;

public class DynamicSectionDocument extends JebEventSource implements
		ITableDocument {

	SectionHeader header;
	DynamicSection section;
	List<TableRow> rows;

	public DynamicSectionDocument(SectionHeader header) {
		this.header = header;
		section = (DynamicSection) (header.getSection());

		rows = new ArrayList<>();
		for (int index = 0; index < section.getEntries().size(); index++) {

		}
		List<Cell> cells;
		for (DynamicSectionEntry entry : section.getEntries()) {
			cells = new ArrayList<>();
			cells.add(new Cell(entry.getTagString()));
			cells.add(new Cell(entry.getValString()));
			rows.add(new TableRow(cells));
		}
	}

	@Override
	public List<String> getColumnLabels() {
		ArrayList<String> output = new ArrayList<>();
		output.add("Tag");
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
