package com.lakroft;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KamilaPredictorImpl implements KamilaPredictor {
    private final int NUM_OF_CHARS;
    private Map<String, List<CharEntity>> dictionary;

    public KamilaPredictorImpl(int numOfChars) {
        this.NUM_OF_CHARS = numOfChars;
    }

    @Override
    public KamilaPredictor loadDictionary() {
        try {
            dictionary = DictionaryLoader.getDict();
        } catch (Exception e) {
            System.err.println("Dictionary loading error");
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    @Override
    public List<Character> predict(String prefix) {
        List<CharEntity> list = dictionary.get(prefix);
        if(null == list) return Collections.emptyList(); // делать предикт на последний символ префикса или пустой префикс
        list = list.subList(0, Math.min(NUM_OF_CHARS, list.size()));
        return list.stream().map(CharEntity::getNextChar).collect(Collectors.toList());
    }
}
