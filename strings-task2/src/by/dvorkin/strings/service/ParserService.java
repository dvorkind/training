package by.dvorkin.strings.service;

import by.dvorkin.strings.composite.Text;
import by.dvorkin.strings.composite.TextComponent;
import by.dvorkin.strings.composite.TextItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserService {
    public TextComponent parse(String text) {
        TextComponent allText = new Text("text", 1);
        toParagraphs(allText, text);
        return allText;
    }

    public void toParagraphs(TextComponent allText, String text) {
        Pattern pattern = Pattern.compile("(?miU)^(.*$)");
        Matcher matcher = pattern.matcher(text);
        int paragraphCount = 0;
        while (matcher.find()) {
            paragraphCount++;
            TextComponent paragraph = new Text("paragraph", paragraphCount);
            toSentence(paragraph, matcher.group());
            allText.add(paragraph);
        }
    }

    public void toWords(TextComponent sentence, String text) {
        String wordsRegexp = "(?mU)(?:[_A-Za-z0-9-\\.]*@[_A-Za-z0-9-\\.]+\\.[A-Za-z]{2,})|" +
                "(?:\\+\\d{3}\\(\\d{2}\\)\\d{3}\\-\\d{2}\\-\\d{2})|" +
                "(?:[A-Za-z0-9А-Яа-яЁё\\-\\=_\\^\\$\\~\\`\\>\\<\\|\\№]+)|" +
                "(?:[+])|[\\p{P}]|(?:[\\s])";
        Matcher m = Pattern.compile(wordsRegexp)
                .matcher(text);
        while (m.find()) {
            TextComponent word = new TextItem(m.group(), "word");
            sentence.add(word);
        }
    }

    public void toSentence(TextComponent paragraph, String text) {
        String paragraphRegexp = "(?miU)[^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)";
        Pattern p = Pattern.compile(paragraphRegexp);
        Matcher matcher = p.matcher(text);
        int sentenceCount = 0;
        while (matcher.find()) {
            sentenceCount++;
            TextComponent sentence = new Text("sentence", sentenceCount);
            toWords(sentence, matcher.group()
                    .replaceAll("\\t+", " ")
                    .replaceAll(" +", " ")
                    .replaceAll(" +", " ")
                    .trim());
            paragraph.add(sentence);
        }
    }
}
