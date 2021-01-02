package com.lakroft;

import java.util.List;

public interface KamilaPredictor {
    public abstract KamilaPredictor loadDictionary();
    public abstract List<Character> predict (String prefix);
}
