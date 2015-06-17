package com.pnf.ELFPlugin;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.pnf.ELF.ELF;
import com.pnf.ELF.ELFFile;
import com.pnf.ELF.SectionHeader;
import com.pnf.MIPSPlugin.MIPSPlugin;
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
    public String getDescription() {
        return "Entry Point:" + Integer.toHexString(elf.getHeader().getEntryPoint());
    }
    @Override
    public boolean process() {
        elf = new ELFFile(data);
        /*IUnit target = null;
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
                target = ident.prepare(name, elf.getMem(), unitProcessor, parent);
                break;
            }
        }
        logger.info("%b checking", target instanceof MIPSUnit);
        MIPSUnit check = (MIPSUnit)target;
        check.init(elf.getHeader().getEntryPoint(), ByteOrder.LITTLE_ENDIAN);
        children.add(target);*/
        MIPSUnit mips = new MIPSUnit("MIPS", elf.getMem(), unitProcessor, this, pdm);
        mips.init(elf.getHeader().getEntryPoint(), ByteOrder.LITTLE_ENDIAN);
        mips.process();
        children.add(mips);
        processed = true;
        return true;
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
