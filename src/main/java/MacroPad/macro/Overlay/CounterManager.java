
package MacroPad.macro.Overlay;

import MacroPad.macro.Overlay.CounterOverlay;

import java.util.HashMap;
import java.util.Map;

public class CounterManager {

    private final Map<String, CounterOverlay> counters = new HashMap<>();

    public CounterOverlay createCounter(String name) {
        CounterOverlay counter = new CounterOverlay(name);
        counters.put(name, counter);
        return counter;
    }

    public CounterOverlay getCounter(String name) {
        return counters.get(name);
    }

    public CounterOverlay getOrCreateCounter(String name) {
        return counters.computeIfAbsent(name, CounterOverlay::new);
    }

    public void toggle(String name) {
        CounterOverlay counter = counters.get(name);

        if (counter != null) {
            counter.toggle();
        }
    }

    public void increment(String name) {
        CounterOverlay counter = counters.get(name);

        if (counter != null) {
            counter.increment();
        }
    }

    public void decrement(String name) {
        CounterOverlay counter = counters.get(name);

        if (counter != null) {
            counter.decrement();
        }
    }

    public void reset(String name) {
        CounterOverlay counter = counters.get(name);

        if (counter != null) {
            counter.reset();
        }
    }
}
