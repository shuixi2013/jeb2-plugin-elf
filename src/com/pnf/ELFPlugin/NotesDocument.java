package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.NoteSection;
import com.pnf.ELF.NoteSectionEntry;
import com.pnf.ELF.SectionHeader;
import com.pnfsoftware.jeb.core.events.JebEventSource;
import com.pnfsoftware.jeb.core.output.text.ICoordinates;
import com.pnfsoftware.jeb.core.output.text.ITextDocument;
import com.pnfsoftware.jeb.core.output.text.ITextDocumentPart;
import com.pnfsoftware.jeb.core.output.text.impl.Anchor;
import com.pnfsoftware.jeb.core.output.text.impl.Line;
import com.pnfsoftware.jeb.core.output.text.impl.TextDocumentPart;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;


public class NotesDocument extends JebEventSource implements ITextDocument {
    private static final ILogger logger = GlobalLog.getLogger(NotesDocument.class);

    private List<Line> lines;
    private List<Anchor> anchors;

    public NotesDocument(SectionHeader section) {
        lines = new ArrayList<>();
        anchors = new ArrayList<>();

        anchors.add(new Anchor(0, 0));
        for(NoteSectionEntry entry : ((NoteSection)(section.getSection())).getEntries()) {
            for(String line1 : entry.toString().split("\n")) {
                for(String line2 : line1.split("\r")) {
                    lines.add(new Line(line2));
                }
            }
        }
    }

    public ICoordinates addressToCoordinates(String address) {
        return null;
    }

    public String coordinatesToAddress(ICoordinates coordinates) {
        return null;
    }

    public long getAnchorCount() {
        return anchors.size();
    }

    public ITextDocumentPart getDocumentPart(long anchorId, int linesAfter) {
        return getDocumentPart(anchorId, linesAfter, 0);
    }
    public ITextDocumentPart getDocumentPart(long anchorId, int linesAfter, int linesBefore) {
        return new TextDocumentPart(lines, anchors);
    }

    @Override
    public void dispose() {
    }
}
