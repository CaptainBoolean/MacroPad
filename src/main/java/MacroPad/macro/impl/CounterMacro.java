package MacroPad.macro.impl;

import MacroPad.macro.Macro;
import MacroPad.macro.Overlay.CounterManager;

public class CounterMacro implements Macro {

    public enum Action {
        TOGGLE,
        INCREMENT,
        DECREMENT,
        RESET
    }

    private final CounterManager counterManager;
    private final String counterName;
    private final Action action;

    public CounterMacro (CounterManager counterManager, String counterName, Action action){
        this.counterManager = counterManager;
        this.counterName = counterName;
        this.action = action;
    }

    @Override
    public void execute() {
        switch (action) {
            case TOGGLE -> counterManager.toggle(counterName);
            case INCREMENT -> counterManager.increment(counterName);
            case DECREMENT -> counterManager.decrement(counterName);
            case RESET -> counterManager.reset(counterName);
        }
    }
}
