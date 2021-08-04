package by.dvorkin.strings.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Text implements TextComponent {
    private List<TextComponent> textComponents = new ArrayList<>();
    private String type;
    private int number;

    public Text(String type, int number) {
        this.type = type;
        this.number = number;
    }

    public void add(TextComponent textComponent) {
        textComponents.add(textComponent);
    }

    public int getSizeComponents() {
        return textComponents.size();
    }

    public TextComponent getChild(int i) {
        return textComponents.get(i);
    }

    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public void print() {
        for (TextComponent textComponent : textComponents) {
            textComponent.print();
        }
    }

    public Iterator<TextComponent> createIterator() {
        return new TextIterator(textComponents.iterator());
    }
}
