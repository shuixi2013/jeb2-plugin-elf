package com.pnf.ELFPlugin;

import com.pnfsoftware.jeb.core.PluginInformation;
import com.pnfsoftware.jeb.core.properties.IPropertyDefinitionManager;
import com.pnfsoftware.jeb.core.properties.IPropertyManager;
import com.pnfsoftware.jeb.core.units.AbstractUnitIdentifier;
import com.pnfsoftware.jeb.core.units.IBinaryFrames;
import com.pnfsoftware.jeb.core.units.IUnit;
import com.pnfsoftware.jeb.core.units.IUnitProcessor;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;

public class ELFPlugin extends AbstractUnitIdentifier {
    private static final ILogger logger = GlobalLog.getLogger(ELFPlugin.class);
    static {
        System.out.println("ELFPlugin Loaded");
    }


    public ELFPlugin() {
        super("ELF_file", 0);
    }

    @Override
    public PluginInformation getPluginInformation() {
        return new PluginInformation("ELF File Unit", "", "1.0", "PNF Software");
    }

    @Override
    public void initialize(IPropertyDefinitionManager parent, IPropertyManager pm) {
        super.initialize(parent, pm);
    }
    

    @Override
    public boolean identify(byte[] data, IUnit parent) {
        return checkBytes(data, 0, (byte)0x7F, 'E', 'L', 'F');
    }
    @Override
    public IUnit prepare(String name, byte[] data, IUnitProcessor unitProcessor, IUnit parent) {
        ELFUnit unit = new ELFUnit(name, data, unitProcessor, parent, pdm);
        unit.process();
        return unit;
    }

    @Override
    public IUnit reload(IBinaryFrames serializedData, IUnitProcessor unitProcessor, 
            IUnit parent) {
        return null;
    }
}
