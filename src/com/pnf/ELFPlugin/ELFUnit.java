package com.pnf.ELFPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pnf.ELF.ELF;
import com.pnf.ELF.ELFFile;
import com.pnf.ELF.SectionHeader;
import com.pnf.ELF.SymbolTableEntry;
import com.pnf.ELF.SymbolTableSection;
import com.pnfsoftware.jeb.core.actions.ActionContext;
import com.pnfsoftware.jeb.core.actions.IActionData;
import com.pnfsoftware.jeb.core.input.BytesInput;
import com.pnfsoftware.jeb.core.input.IInput;
import com.pnfsoftware.jeb.core.output.AbstractUnitRepresentation;
import com.pnfsoftware.jeb.core.output.IGenericDocument;
import com.pnfsoftware.jeb.core.output.IUnitFormatter;
import com.pnfsoftware.jeb.core.output.UnitFormatterAdapter;
import com.pnfsoftware.jeb.core.properties.IPropertyDefinitionManager;
import com.pnfsoftware.jeb.core.units.AbstractBinaryUnit;
import com.pnfsoftware.jeb.core.units.IBinaryFrames;
import com.pnfsoftware.jeb.core.units.IInteractiveUnit;
import com.pnfsoftware.jeb.core.units.IUnit;
import com.pnfsoftware.jeb.core.units.IUnitIdentifier;
import com.pnfsoftware.jeb.core.units.IUnitProcessor;
import com.pnfsoftware.jeb.core.units.codeobject.ICodeObjectUnit;
import com.pnfsoftware.jeb.core.units.codeobject.ILoaderInformation;
import com.pnfsoftware.jeb.core.units.codeobject.ISymbolInformation;
import com.pnfsoftware.jeb.util.IO;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ELFUnit extends AbstractBinaryUnit implements ICodeObjectUnit, IInteractiveUnit {
    private static final ILogger logger = GlobalLog.getLogger(ELFUnit.class);
    private ELFFile elf;
    private List<ISymbolInformation> symbols;
    private List<ELFSectionInfo> sections;
    private List<ELFSectionInfo> segments;
    private ELFLoaderInformation loaderInfo;
    private byte[] data;

    public ELFUnit(String name, IInput input, IUnitProcessor unitProcessor, IUnit parent, IPropertyDefinitionManager pdm) {
        super("", input, "ELF_file", name, unitProcessor, parent, pdm);
        try(InputStream stream = input.getStream()) {
            data = IO.readInputStream(stream);
        }
        catch(IOException e) {
            logger.catching(e);
        }
    }

    public ELFUnit(IBinaryFrames serializedData, IUnitProcessor unitProcessor, IUnit parent, IPropertyDefinitionManager pdm) {
        super(serializedData, unitProcessor, parent, pdm);
    }

    @Override
    public boolean process() {
        elf = new ELFFile(data);
        symbols = new ArrayList<>();
        sections = new ArrayList<>();
        segments = new ArrayList<>();
        SymbolTableSection symtab = null;
        for(SectionHeader header : elf.getSectionHeaderTable().getHeaders()) {
            if(header.getType() == ELF.SHT_DYNSYM || header.getType() == ELF.SHT_SYMTAB) {
                symtab = (SymbolTableSection)(header.getSection());
                for(SymbolTableEntry entry : symtab.getEntries()) {
                    symbols.add(new SymbolInfo(entry.getName(), entry.getValue(), entry.getType(), entry.getSize()));
                }
            }
            sections.add(new ELFSectionInfo(header));
            if(header.getAddress() != 0) {
                segments.add(new ELFSectionInfo(header));
            }
        }
        loaderInfo = new ELFLoaderInformation(elf);
        IUnit target = null;
        String targetType;
        switch(elf.getArch()) {
            case ELF.EM_MIPS:
                targetType = "MIPS";
                break;
            default:
                processed = false;
                return false;
        }
        for(SectionHeader header : elf.getSectionHeaderTable().getHeaders()) {
            if(header.getSection() != null) {
                unitProcessor.process(header.getName(), new BytesInput(header.getSection().getBytes()), this, null, true);
            }
        }
        for(IUnitIdentifier ident: unitProcessor.getUnitIdentifiers()) {
            if(targetType.equals(ident.getFormatType())) {
                target = ident.prepare(name, new BytesInput(data), unitProcessor, this);
                break;
            }
        }
        reparseUnits.add(target);
        processed = true;
        return true;
    }
    public ELFFile getElf() {
        return elf;
    }

    @Override
    public List<ISymbolInformation> getExportedSymbols() {
        return symbols;
    }
    @Override
    public List<ISymbolInformation> getImportedSymbols() {
        return null;
    }

    @Override
    public ILoaderInformation getLoaderInformation() {
        return new ELFLoaderInformation(elf);
    }
    @Override
    public List<ELFSectionInfo> getSections() {
        return sections;
    }
    @Override
    public List<ELFSectionInfo> getSegments() {
        return segments;
    }







    @Override
    public IBinaryFrames serialize() {
        return null;
    }
    @Override
    public IUnitFormatter getFormatter() {
        List<SectionHeader> sectionHeaders = elf.getSectionHeaderTable().getHeaders();
        UnitFormatterAdapter formatter = new UnitFormatterAdapter();
        formatter.addDocumentPresentation(new AbstractUnitRepresentation("Section Header Table", true) {
            @Override
            public IGenericDocument getDocument() {
                return new SectionHeaderTableDocument(elf.getSectionHeaderTable());
            }
        });

        formatter.addDocumentPresentation(new AbstractUnitRepresentation("Program Header Table", false) {
            @Override
            public IGenericDocument getDocument() {
                return new ProgramHeaderTableDocument(elf.getProgramHeaderTable());
            }
        });
        for(SectionHeader section : sectionHeaders) {
            switch(section.getType()) {
                case ELF.SHT_STRTAB:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IGenericDocument getDocument() {
                            return new StringTableDocument(section);
                        }
                    });
                    break;
                case ELF.SHT_HASH:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IGenericDocument getDocument() {
                            return new HashTableDocument(section);
                        }
                    });
                    break;
                case ELF.SHT_NOTE:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IGenericDocument getDocument() {
                            return new NotesDocument(section);
                        }
                    });
                    break;

                case ELF.SHT_DYNSYM:
                case ELF.SHT_SYMTAB:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IGenericDocument getDocument() {
                            return new SymbolTableDocument(section);
                        }
                    });
                    break;
                case ELF.SHT_DYNAMIC:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IGenericDocument getDocument() {
                            return new DynamicSectionDocument(section);
                        }
                    });
                    break;
            }
        }

        return formatter;
    }

    // No actions available at the moment
    public boolean executeAction(ActionContext context, IActionData data) {
        return false;
    }

    public boolean prepareExecution(ActionContext context, IActionData data) {
        return false;
    }

    public boolean canExecuteAction(ActionContext context) {
        return false;
    }

    @Override
    public List<Integer> getItemActions(long id) {
        return new ArrayList<>();
    }
    @Override
    public long getItemAtAddress(String address) {
        
        return 1L;
    }
    @Override
    public String getAddressOfItem(long id) {
        return null;
    }
    @Override
    public List<Integer> getGlobalActions() {
        return new ArrayList<>();
    }
    @Override
    public List<Integer> getAddressActions(String address) {
        return new ArrayList<>();
    }

    @Override
    public long convertFileOffsetToRelativeAddress(long offset) {
        return 0;
    }
    @Override
    public long convertRelativeAddressToFileOffset(long offset) {
        return 0;
    }
    @Override
    public String getComment(String address) {
        return null;
    }
    @Override
    public Map<String, String> getComments() {
        return null;
    }

    @Override
    public String getAddressLabel(String address) {
        return null;
    }
    @Override
    public Map<String, String> getAddressLabels() {
        return null;
    }
}
