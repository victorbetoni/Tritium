package net.victorbetoni.tritium.util;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class Configurable<TYPE> {

    protected TYPE config;
    private Class<TYPE> type;
    private File file;

    protected Configurable(Class<TYPE> type, String path) {
        this.type = type;
        try {
            file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            read();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getIntegrationConfigPath(String id) {
        return System.getProperty("user.dir") + "\\config\\" + id + "\\config.json";
    }

    public static String getModuleConfigPath(String integration, String id) {
        return System.getProperty("user.dir") + "\\config\\" + integration + "\\modules\\" + id + ".json";
    }

    public TYPE getConfig() {
        return config;
    }

    public void read() {
        try {
            String content = Files.readString(file.toPath());
            Gson gson = new Gson().newBuilder().registerTypeAdapter(File.class, new FileTypeAdapter()).create();
            config = (gson.fromJson(content, type));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(config);
            Files.writeString(file.toPath(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
