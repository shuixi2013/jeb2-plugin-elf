package com.pnf.ELFPlugin;

import com.pnf.ELF.ELF;
import com.pnfsoftware.jeb.core.IUnitCreator;
import com.pnfsoftware.jeb.core.PluginInformation;
import com.pnfsoftware.jeb.core.input.IInput;
import com.pnfsoftware.jeb.core.properties.IPropertyDefinitionManager;
import com.pnfsoftware.jeb.core.properties.IPropertyManager;
import com.pnfsoftware.jeb.core.units.AbstractUnitIdentifier;
import com.pnfsoftware.jeb.core.units.IUnit;
import com.pnfsoftware.jeb.core.units.IUnitProcessor;
import com.pnfsoftware.jeb.core.units.WellKnownUnitTypes;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ELFPlugin extends AbstractUnitIdentifier {
    private static final ILogger logger = GlobalLog.getLogger(ELFPlugin.class);

    public static final String TYPE = WellKnownUnitTypes.typeLinuxElf;

    public ELFPlugin() {
        super(TYPE, 0);
    }

    @Override
    public PluginInformation getPluginInformation() {
        return new PluginInformation("ELF File Unit", "Linux ELF parser plugin", "1.0", "PNF Software");
    }
    @Override
    public void initialize(IPropertyDefinitionManager parent) {
        super.initialize(parent);
    }
    

    @Override
    public boolean canIdentify(IInput input, IUnitCreator parent) {
        return 
            checkBytes(input, 0, (int)ELF.ElfMagic[0], (int)ELF.ElfMagic[1], (int)ELF.ElfMagic[2], (int)ELF.ElfMagic[3]) &&
            // Ensure a 32 bit elf file
            checkBytes(input, 4, (byte)0x1)
            ;
    }
    @Override
    public IUnit prepare(String name, IInput input, IUnitProcessor unitProcessor, IUnitCreator parent) {
        ELFUnit unit = new ELFUnit(name, input, unitProcessor, parent, pdm);
        unit.process();
        return unit;
    }

}
