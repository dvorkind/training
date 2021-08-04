package by.dvorkin.strings.entities;

import java.util.Iterator;

public class NullIterator implements Iterator<TextComponent> {

    public TextComponent next() {
        return null;
    }

    public boolean hasNext() {
        return false;
    }
}
