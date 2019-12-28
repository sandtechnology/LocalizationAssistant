package pers.gwyog.localizationassistant.utils;

public class PairHolder<V1, V2> {
    private V1 first;
    private V2 last;

    PairHolder(V1 first, V2 last) {
        this.first = first;
        this.last = last;
    }

    public V1 first() {
        return first;
    }

    public V2 last() {
        return last;
    }
}
