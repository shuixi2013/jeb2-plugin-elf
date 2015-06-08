package com.pnf.ELF;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.pnfsoftware.jeb.core.actions.InformationForActionExecution;
import com.pnfsoftware.jeb.core.output.AbstractUnitRepresentation;
import com.pnfsoftware.jeb.core.output.IInfiniDocument;
import com.pnfsoftware.jeb.core.output.IUnitFormatter;
import com.pnfsoftware.jeb.core.output.UnitFormatterAdapter;
import com.pnfsoftware.jeb.core.properties.IPropertyDefinitionManager;
import com.pnfsoftware.jeb.core.units.AbstractBinaryUnit;
import com.pnfsoftware.jeb.core.units.IBinaryFrames;
import com.pnfsoftware.jeb.core.units.IInteractiveUnit;
import com.pnfsoftware.jeb.core.units.IUnit;
import com.pnfsoftware.jeb.core.units.IUnitProcessor;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;


public class ELFUnit extends AbstractBinaryUnit implements IInteractiveUnit {
    private static final ILogger logger = GlobalLog.getLogger(ELFUnit.class);
    private ELFFile elf;
    public ELFUnit(String name, byte[] data, IUnitProcessor unitProcessor, IUnit parent, IPropertyDefinitionManager pdm) {
        super("", data, "ELF_file", name, unitProcessor, parent, pdm);
    }
    public ELFUnit(IBinaryFrames serializedData, IUnitProcessor unitProcessor, IUnit parent, IPropertyDefinitionManager pdm) {
        super(serializedData, unitProcessor, parent, pdm);
    }
    @Override
    public boolean process() {
        elf = new ELFFile(data);
        for(SectionHeader section : elf.getSectionHeaderTable().getHeaders()) {
            switch(section.getType()) {
                case ELF.SHT_PROGBITS:
                    unitProcessor.process(section.getName(), section.getSection().getBytes(), this);
                    break;
            }
        }
        return false;
    }

    @Override
    public String getNotes() {
        return "";
    }
    @Override
    public IBinaryFrames serialize() {
        return null;
    }
    @Override
    public IUnitFormatter getFormatter() {
        List<SectionHeader> sectionHeaders = elf.getSectionHeaderTable().getHeaders();
        UnitFormatterAdapter formatter = new UnitFormatterAdapter();

        for(SectionHeader section : sectionHeaders) {
            switch(section.getType()) {
                case ELF.SHT_STRTAB:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IInfiniDocument getDocument() {
                            return new StringTableDocument(section);
                        }
                    });
                    break;
            }
        }
        /*formatter.addDocumentPresentation(new AbstractUnitRepresentation("Tree view", true) {
            @Override
            public IInfiniDocument getDocument() {
                return new TextFileTreeDocument(TextFileUnit.this);
            }
        });*/
        /*formatter.addDocumentPresentation(new AbstractUnitRepresentation("Formatted Text", true) {
            @Override
            public IInfiniDocument getDocument() {
                return new TextFileDocument(TextFileUnit.this);
            }
        });*/
        return formatter;
    }

    @Override
    public boolean executeAction(InformationForActionExecution info) {
        return false;
    }

    @Override
    public boolean prepareExecution(InformationForActionExecution info) {
        return false;
    }

    @Override
    public List<Integer> getItemActions(long id) {
        return new ArrayList<>();
    }

}
