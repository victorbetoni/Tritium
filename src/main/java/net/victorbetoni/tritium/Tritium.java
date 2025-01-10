package net.victorbetoni.tritium;

import com.google.gson.Gson;
import net.victorbetoni.tritium.integration.Integration;
import net.victorbetoni.tritium.integration.factory.IntegrationFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Tritium {

    private static List<? extends Integration<?, ?, ?>> enabledIntegrations;
    private static IntegratorConfig config;
    private static boolean integrating = false;
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        loadConfig();

        enabledIntegrations = Arrays.stream(config.enabledModules())
                .map(x -> x.split("\\.")[0])
                .map(IntegrationFactory::createIntegrationById).toList();

        enabledIntegrations.forEach(integration -> {
            integration.setupModules(Arrays.stream(config.enabledModules())
                    .filter(x -> x.startsWith(integration.getId()))
                    .map(x -> x.split("\\.")[1]).toList());
        });

        enabledIntegrations.forEach(x -> x.getEnabledModules().forEach(y ->{
            //y.getScreen().buildScreen();
            y.run();
        }));

    }

    public static void loadConfig() {
        try {
            File file = new File(System.getProperty("user.dir") + "\\config\\config.json");
            String content = Files.readString(file.toPath());
            Gson gson = new Gson();
            config = gson.fromJson(content, IntegratorConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}