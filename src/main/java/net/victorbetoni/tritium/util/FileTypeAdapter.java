package net.victorbetoni.tritium.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;

public class FileTypeAdapter extends TypeAdapter<File> {

    @Override
    public void write(JsonWriter out, File file) throws IOException {
        out.value(file.getPath());
    }

    @Override
    public File read(JsonReader in) throws IOException {
        return new File(in.nextString());
    }
}