package by.dvorkin.strings.composite;

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

    @Override
    public void add(TextComponent textComponent) {
        textComponents.add(textComponent);
    }

    @Override
    public int getSizeComponents() {
        return textComponents.size();
    }

    @Override
    public TextComponent getChild(int i) {
        return textComponents.get(i);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Iterator<TextComponent> createIterator() {
        return new TextIterator(textComponents.iterator());
    }
}
