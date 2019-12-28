package pers.gwyog.localizationassistant.filesAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class LangAdapter extends AbstractFileAdapter {
    @Override
    public void write(LangDataMap<String, String> langMap, Path path) throws IOException {
        Files.write(path, langMap.toStringList((k, v) -> k + "=" + v));
    }

    @Override
    public LangDataMap<String, String> read(Path path) throws IOException {

        return Files.readAllLines(path).stream().map(s ->
        {
            if (s.contains("=")) {
                return s.split("=", 2);
            } else {
                //对非词条文本进行处理
                return new String[]{s};
            }
        }).collect(LangDataMap::new, (map, strings) -> {
            if (strings.length == 1) {
                map.addComment(strings[0]);
            } else {
                map.put(strings[0], strings[1]);
            }
        }, HashMap::putAll);
    }
}
