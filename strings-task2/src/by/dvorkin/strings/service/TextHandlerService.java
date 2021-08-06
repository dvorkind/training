package by.dvorkin.strings.service;

import by.dvorkin.strings.composite.TextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class TextHandlerService {
    private TextComponent allText;
    private HashSet<String> notWord;

    public TextHandlerService(TextComponent allText) {
        this.allText = allText;
        notWord = new HashSet<>();
        Collections.addAll(notWord, "„", "“", "’", "–", "…", ",", "—", " ", "!", "[", "]", "{", "}",
                "\"", "№", ";", "%", ":", "?", "~", "`", "@", "#", "$", "%", "^", "&", "*", "”", "°",
                "(", ")", "_", "+", "-", "=", "\\", "|", "/", ".", "<", ">", "'", "»", "«", "•");
    }

    public String modifiedText() {
        Iterator<TextComponent> iterator = allText.createIterator();
        int paragraphNumber = 0;
        StringBuilder modifiedText = new StringBuilder();
        while (iterator.hasNext()) {
            TextComponent textComponent = iterator.next();
            if (textComponent.getType().equals("paragraph")) {
                paragraphNumber = textComponent.getSizeComponents();
                if (textComponent.getNumber() > 1) {
                    modifiedText.append("\n");
                }
            }
            if (textComponent.getType()
                    .equals("sentence")) {
                if (textComponent.getNumber() > 1 && paragraphNumber != textComponent.getNumber()) {
                    modifiedText.append(" ");
                }
                List<String> sentenceList = new ArrayList<>();
                for (int i = 0; i < textComponent.getSizeComponents(); i++) {
                    String word = textComponent.getChild(i)
                            .getWord();
                    sentenceList.add(word);
                }
                if (sentenceList.size() > 0) {
                    modifiedText.append(switchWords(sentenceList));
                }
            }
        }
        return modifiedText.toString();
    }

    public String getStatistics() {
        int sentenceCount = 0;
        int paragraphCount = 0;
        int charCount = 0;
        int wordCount = 0;
        int paragraphNumber = 0;
        Iterator<TextComponent> iterator = allText.createIterator();
        while (iterator.hasNext()) {
            TextComponent textComponent = iterator.next();
            if (textComponent.getType()
                    .equals("paragraph")) {
                paragraphNumber = textComponent.getSizeComponents();
                paragraphCount++;
            }
            if (textComponent.getType()
                    .equals("sentence")) {
                sentenceCount++;
                if (textComponent.getNumber() > 1 && paragraphNumber != textComponent.getNumber()) {
                    charCount++;
                }
                for (int i = 0; i < textComponent.getSizeComponents(); i++) {
                    String word = textComponent.getChild(i)
                            .getWord();
                    charCount += word.length();
                    if (!isNotWord(word)) {
                        wordCount++;
                    }
                }
            }
        }
        return String.format("Total paragraphs: %,d\nTotal sentences: %,d\nTotal words: %,d\nTotal chars: %,d", paragraphCount, sentenceCount, wordCount, charCount);
    }

    public Boolean isNotWord(String string) {
        return notWord.contains(string);
    }

    public String switchWords(List<String> list) {
        int iLastWord = list.size() - 1;
        while (iLastWord > 0 && isNotWord(list.get(iLastWord))) {
            iLastWord--;
        }
        int iFirstWord = 0;
        while (iFirstWord <= list.size() - 1 && isNotWord(list.get(iFirstWord))) {
            iFirstWord++;
        }
        if (iLastWord == 0 && iFirstWord == list.size()) {
            StringBuilder result = new StringBuilder();
            for (String string : list) {
                result.append(string);
            }
            return result.toString();
        }
        Collections.swap(list, iFirstWord, iLastWord);
        StringBuilder result = new StringBuilder();
        for (String string : list) {
            result.append(string);
        }
        return result.toString();
    }
}
