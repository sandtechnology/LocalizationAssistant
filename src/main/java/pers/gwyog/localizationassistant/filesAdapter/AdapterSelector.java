package pers.gwyog.localizationassistant.filesAdapter;

import pers.gwyog.localizationassistant.utils.PathProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class AdapterSelector {
    private static Map<String, AbstractFileAdapter> fileAdapterMap = new HashMap<>();

    static {
        fileAdapterMap.put(".json", new JsonAdapter());
        fileAdapterMap.put(".lang", new LangAdapter());
    }

    private AdapterSelector() {
    }

    private static AbstractFileAdapter decideAdapter(Path path) {
        PathProcessor pathProcessor = new PathProcessor(path);
        return fileAdapterMap.getOrDefault(pathProcessor.getFileExt(), fileAdapterMap.get(".lang"));
    }

    public static LangDataMap<String, String> read(Path path) throws IOException {
        if (Files.notExists(path)) {
            throw new FileNotFoundException();
        }
        return decideAdapter(path).read(path);
    }

    public static void write(LangDataMap<String, String> langMap, Path path) throws IOException {
        if (Files.notExists(path)) {
            Files.createFile(path);
        }
        decideAdapter(path).write(langMap, path);
    }
}
