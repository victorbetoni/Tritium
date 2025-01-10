package net.victorbetoni.tritium.integration.loader;

import net.victorbetoni.tritium.integration.Integration;
import net.victorbetoni.tritium.platform.Platform;
import net.victorbetoni.tritium.util.Configurable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IntegrationExtensionLoader {

    private File extensionFile;

    private Class<? extends Integration<? extends Platform,? extends Platform, ? extends Configurable<?>>> extensionMainClass;

    public IntegrationExtensionLoader(File extensionFile) {
        this.extensionFile = extensionFile;
    }

    public void load() throws IOException, ClassNotFoundException {
        ZipFile jar = new ZipFile(extensionFile);
        Enumeration<? extends ZipEntry> entries = jar.entries();

        URL[] urls = { new URL("jar:file:" + extensionFile.toPath().toString() + "!/") };
        URLClassLoader classLoader = new URLClassLoader(urls);

        ZipEntry entry = null;
        boolean mainClassFound = false;
        while((entry = entries.nextElement()) != null) {
            if(entry.getName().endsWith(".class")) {
                String name = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                Class<?> clazz = classLoader.loadClass(name);
                if(Integration.class.isAssignableFrom(clazz)) {
                    if(mainClassFound) {
                        throw new IOException("extension cant have two classes that extends Extesion.");
                    }
                    extensionMainClass = (Class<? extends Integration<? extends Platform,? extends Platform, ? extends Configurable<?>>>) clazz;
                    mainClassFound = true;
                }
            }
        }
    }

}
