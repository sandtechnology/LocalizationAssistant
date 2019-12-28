package pers.gwyog.localizationassistant.filesAdapter;

import java.util.*;
import java.util.function.BiFunction;

public class LangDataMap<K, V> extends LinkedHashMap<K, V> {
    private LinkedHashMap<Integer, List<V>> commentMap = new LinkedHashMap<>();

    public LangDataMap() {
        super();
    }


    /**
     * 添加注释
     *
     * @param str 要添加的注释内容
     */
    public void addComment(V str) {
        if (commentMap.containsKey(super.size())) {
            commentMap.get(super.size()).add(str);
        } else {
            commentMap.put(super.size(), new ArrayList<>(Collections.singleton(str)));
        }
    }

    /**
     * 将语言文件转换为List
     *
     * @param mappingFunction 对mapping使用的函数
     * @return 转换后的List
     */
    public List<String> toStringList(BiFunction<K, V, V> mappingFunction) {
        List<String> list = new LinkedList<>();
        //监测注释是否添加完成的计数器
        int counter = 0;
        //辅助添加的计数器
        int i = 0;
        //伴随词条添加注释
        for (Map.Entry<K, V> entry : super.entrySet()) {
            if (commentMap.get(i) != null) {
                commentMap.get(i).stream().map(V::toString).forEach(list::add);
                counter++;
            }
            list.add(mappingFunction.apply(entry.getKey(), entry.getValue()).toString());
            i++;
        }
        //添加剩余的注释
        while (counter != commentMap.size()) {
            list.add(commentMap.get(i).toString());
            i++;
            counter++;
        }
        return list;
    }

    @Override
    public int size() {
        //对size进行修正
        return super.size() + commentMap.values().stream().mapToInt(List::size).sum();
    }
}
