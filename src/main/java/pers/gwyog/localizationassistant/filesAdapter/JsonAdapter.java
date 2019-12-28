package pers.gwyog.localizationassistant.filesAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

public class JsonAdapter extends AbstractFileAdapter {

    //Gson的缩进格式Json输出
    //https://blog.k-res.net/archives/1653.html
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    LangDataMap<String, String> read(Path path) throws IOException {
        return gson.fromJson(String.join("", Files.readAllLines(path)), new TypeToken<LangDataMap<String, String>>() {
        }.getType());
    }

    @Override
    void write(LangDataMap<String, String> langMap, Path path) throws IOException {
        Files.write(path, Collections.singleton(gson.toJson(langMap)));
    }
}
