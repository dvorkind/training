package by.dvorkin.strings.composite;

import java.util.Iterator;

public class TextItem implements TextComponent {
    private String word;
    private String type;

    public TextItem(String word, String type) {
        this.word = word;
        this.type = type;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Iterator<TextComponent> createIterator() {
        return new NullIterator();
    }
}
