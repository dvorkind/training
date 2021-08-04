package by.dvorkin.strings.entities;

import java.util.Iterator;

public class TextItem implements TextComponent {
    private String word;
    private String type;

    public TextItem(String word, String type) {
        this.word = word;
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public String getType() {
        return type;
    }

    public void print() {
        System.out.print(getWord());
    }

    public Iterator<TextComponent> createIterator() {
        return new NullIterator();
    }
}
