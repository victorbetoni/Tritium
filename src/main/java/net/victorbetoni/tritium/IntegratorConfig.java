package net.victorbetoni.tritium;

import com.google.gson.annotations.SerializedName;

public class IntegratorConfig {

    @SerializedName("enabled_modules") public String[] enabledModules;
    @SerializedName("integration_interval") public int integrationInterval;

    public String[] enabledModules() {
        return enabledModules;
    }

    public int integrationInterval() {
        return integrationInterval;
    }
}
