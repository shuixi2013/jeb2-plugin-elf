package com.pnf.ELFPlugin;

import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.ELF;
import com.pnf.ELF.ELFFile;
import com.pnf.ELF.SectionHeader;
import com.pnf.ELF.SymbolTableEntry;
import com.pnf.ELF.SymbolTableSection;
import com.pnf.MIPSPlugin.MIPSUnit;
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
import com.pnfsoftware.jeb.core.units.IUnitIdentifier;
import com.pnfsoftware.jeb.core.units.IUnitProcessor;
import com.pnfsoftware.jeb.core.units.codeloader.ICodeLoaderUnit;
import com.pnfsoftware.jeb.core.units.codeloader.ILoaderInformation;
import com.pnfsoftware.jeb.core.units.codeloader.ISymbolInformation;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ELFUnit extends AbstractBinaryUnit implements ICodeLoaderUnit, IInteractiveUnit {
    private static final ILogger logger = GlobalLog.getLogger(ELFUnit.class);
    private ELFFile elf;
    private List<ISymbolInformation> symbols;
    private List<ELFSectionInfo> sections;
    private List<ELFSectionInfo> segments;
    private ELFLoaderInformation loaderInfo;

    public ELFUnit(String name, byte[] data, IUnitProcessor unitProcessor, IUnit parent, IPropertyDefinitionManager pdm) {
        super("", data, "ELF_file", name, unitProcessor, parent, pdm);
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
                    symbols.add(new SymbolInfo(entry.getName(), entry.getValue(), entry.getType()));
                }
            }
            sections.add(new ELFSectionInfo(header));
            if(header.getAddress() != 0) {
                segments.add(new ELFSectionInfo(header));
            }
        }
        loaderInfo = new ELFLoaderInformation(elf);
        /* MIPSUnit mips = new MIPSUnit("MIPS", elf.getImage(), unitProcessor, this, pdm);
        mips.process();
        children.add(mips);*/ 
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
        for(IUnitIdentifier ident: unitProcessor.getUnitIdentifiers()) {
            if(targetType.equals(ident.getFormatType())) {
                target = ident.prepare(name, elf.getImage(), unitProcessor, this);
                break;
            }
        }
        MIPSUnit check = (MIPSUnit)target;
        reparseUnits.add(check);
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
            public IInfiniDocument getDocument() {
                return new SectionHeaderTableDocument(elf.getSectionHeaderTable());
            }
        });

        formatter.addDocumentPresentation(new AbstractUnitRepresentation("Program Header Table", false) {
            @Override
            public IInfiniDocument getDocument() {
                return new ProgramHeaderTableDocument(elf.getProgramHeaderTable());
            }
        });
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
                case ELF.SHT_HASH:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IInfiniDocument getDocument() {
                            return new HashTableDocument(section);
                        }
                    });
                    break;
                case ELF.SHT_NOTE:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IInfiniDocument getDocument() {
                            return new NotesDocument(section);
                        }
                    });
                    break;

                case ELF.SHT_DYNSYM:
                case ELF.SHT_SYMTAB:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IInfiniDocument getDocument() {
                            return new SymbolTableDocument(section);
                        }
                    });
                    break;
                case ELF.SHT_DYNAMIC:
                    formatter.addDocumentPresentation(new AbstractUnitRepresentation(section.getName(), false) {
                        @Override
                        public IInfiniDocument getDocument() {
                            return new DynamicSectionDocument(section);
                        }
                    });
                    break;
            }
        }

        return formatter;
    }

    // No actions available at the moment
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

}
