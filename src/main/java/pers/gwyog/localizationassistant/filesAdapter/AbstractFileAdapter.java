package pers.gwyog.localizationassistant.filesAdapter;

import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractFileAdapter {
    abstract LangDataMap<String, String> read(Path path) throws IOException;

    abstract void write(LangDataMap<String, String> langMap, Path path) throws IOException;

    @Override
    public String toString() {
        return this.getClass().getName();
    }

}
