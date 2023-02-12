package me.woutergritter.hueambiance.util;

import java.util.function.Supplier;

public class CachedMethod<R> {

    private final long updateInterval;

    private long lastUpdate;
    private R lastValue;

    public CachedMethod(long updateInterval) {
        this.updateInterval = updateInterval;
    }

    public R getValue(Supplier<R> refreshMethod) {
        long now = System.currentTimeMillis();
        if (lastUpdate == 0 || now - lastUpdate > updateInterval) {
            lastUpdate = now;
            lastValue = refreshMethod.get();
        }

        return lastValue;
    }
}
