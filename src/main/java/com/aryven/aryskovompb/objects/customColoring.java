package com.aryven.aryskovompb.objects;

import dev.mayuna.mayuslibrary.console.colors.Color;
import dev.mayuna.mayuslibrary.console.colors.Colors;
import dev.mayuna.mayuslibrary.logging.coloring.BaseColoring;
import dev.mayuna.mayuslibrary.logging.coloring.ColoringString;
import dev.mayuna.mayuslibrary.logging.types.*;

public class customColoring extends BaseColoring {
    public customColoring() {
        addColoring(new ColoringString(new InfoLogType(), new Color().setForeground(Colors.LIGHT_CYAN).build(), Color.RESET));
        addColoring(new ColoringString(new WarningLogType(), new Color().setForeground(Colors.LIGHT_YELLOW).build(), Color.RESET));
        addColoring(new ColoringString(new ErrorLogType(), new Color().setForeground(Colors.RED).build(), Color.RESET));
        addColoring(new ColoringString(new DebugLogType(), new Color().setForeground(Colors.DARK_GRAY).build(), Color.RESET));
        addColoring(new ColoringString(new TraceLogType(), new Color().setForeground(Colors.MAGENTA).build(), Color.RESET));
    }
}
