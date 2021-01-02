package com.lakroft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryLoader {
    public static Map<String, List<CharEntity>> getDict () throws IOException {
        Path path = Paths.get("135-0.txt");
        byte[] bytes = Files.readAllBytes(path);
        String text = new String(bytes);
        text = text.toLowerCase();
        Map<String, Map<Character, CharEntity>> tempDict = new HashMap<>();

        Pattern r = Pattern.compile("\\p{javaLowerCase}+");
        Matcher matcher = r.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            insertToDic(word, tempDict);
        }
        return prepairFinalDict(tempDict);
    }

    private static Map<String, List<CharEntity>> prepairFinalDict(
            Map<String, Map<Character, CharEntity>> tempDic) {
        Map<String, List<CharEntity>> finalDict = new HashMap<>();

        for (String prefix : tempDic.keySet()) {
            List<CharEntity> list = new ArrayList<>(tempDic.get(prefix).values());
            list.sort(Comparator.reverseOrder());
            finalDict.put(prefix, list);
        }
        return finalDict;
    }

    private static void insertToDic(String word, Map<String,
            Map<Character, CharEntity>> tempDic) {
        for (int i = 0; i < word.length(); i++) {
            String prefix = word.substring(0, i);
            Character nextChar = word.charAt(i);
            if (!tempDic.containsKey(prefix)) {
                tempDic.put(prefix, new HashMap<>());
            }
            Map<Character, CharEntity> charEntry = tempDic.get(prefix);
            if (!charEntry.containsKey(nextChar)) {
                charEntry.put(nextChar, new CharEntity(nextChar));
            }
            charEntry.get(nextChar).increment();
        }
    }
}
