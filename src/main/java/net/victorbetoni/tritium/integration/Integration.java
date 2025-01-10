package net.victorbetoni.tritium.integration;

import net.victorbetoni.tritium.platform.API;
import net.victorbetoni.tritium.integration.module.AbstractIntegrationModule;
import net.victorbetoni.tritium.integration.module.ModuleScreen;
import net.victorbetoni.tritium.platform.Platform;
import net.victorbetoni.tritium.util.Configurable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Integration<CONSUMER extends Platform,  CONSUMED extends Platform, CONFIG> extends Configurable<CONFIG> {

    protected String id;
    protected ModuleScreen screen;
    protected Map<String, Boolean> moduleStates = new HashMap<>();
    protected Map<String, AbstractIntegrationModule<CONSUMER, CONSUMED,?, ? extends Integration<CONSUMER, CONSUMED, ?>>> modules = new HashMap<>();
    protected CONSUMER consumer;
    protected CONSUMED consumed;

    public Integration(String id, CONSUMER consumer, CONSUMED consumed, Class<CONFIG> configType, String configPath) {
        super(configType, configPath);
        this.id = id;
        this.consumer = consumer;
        this.consumed = consumed;
        try {
            File configFolder = new File(String.format("%s/config/%s", System.getProperty("user.dir"), id));
            if(!configFolder.exists()) {
                configFolder.mkdir();
            }
            File config = new File(configFolder, "config/config.json");
            if (config.exists()) {
                config.createNewFile();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public Map<String, Boolean> getModuleStates() {
        return moduleStates;
    }

    public Map<String, AbstractIntegrationModule<CONSUMER, CONSUMED, ?, ? extends Integration<CONSUMER, CONSUMED, ?>>> getModules() {
        return modules;
    }

    public CONSUMER getConsumer() {
        return consumer;
    }

    public CONSUMED getConsumed() {
        return consumed;
    }

    public List<? extends AbstractIntegrationModule<CONSUMER,CONSUMED,?,? extends Integration<CONSUMER, CONSUMED, ?>>> getEnabledModules() {
        return moduleStates.keySet().stream().filter(moduleStates::get).map(modules::get).toList();
    }

    public abstract void setupModules(List<String> enabled);

}
