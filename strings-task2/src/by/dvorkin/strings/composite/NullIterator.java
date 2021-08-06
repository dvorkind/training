package by.dvorkin.strings.composite;

import java.util.Iterator;

public class NullIterator implements Iterator<TextComponent> {
    @Override
    public TextComponent next() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
