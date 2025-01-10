package net.victorbetoni.tritium.integration.module;

import net.victorbetoni.tritium.integration.Integration;
import net.victorbetoni.tritium.platform.Platform;
import net.victorbetoni.tritium.util.Configurable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class AbstractIntegrationModule<T extends Platform, U extends Platform, W, V extends Integration<T,U,?>> extends Configurable<W> {

    protected String id;
    protected ModuleScreen screen;
    protected final V integration;
    protected long lastIntegration;
    protected ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public AbstractIntegrationModule(String id, V integration, Class<W> configClazz, String path) {
        super(configClazz, path);
        this.integration = integration;
    }

    public V integration() {
        return integration;
    }

    public ModuleScreen getScreen() {
        return screen;
    }

    public abstract void run();

}
