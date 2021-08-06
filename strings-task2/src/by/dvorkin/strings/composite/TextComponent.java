package by.dvorkin.strings.composite;

import java.util.Iterator;

public interface TextComponent {
    default void add(TextComponent textComponent) {
        throw new UnsupportedOperationException();
    }

    default TextComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    default String getType() {
        throw new UnsupportedOperationException();
    }

    default int getNumber() {
        throw new UnsupportedOperationException();
    }

    default int getSizeComponents() {
        throw new UnsupportedOperationException();
    }

    default String getWord() {
        throw new UnsupportedOperationException();
    }

    Iterator<TextComponent> createIterator();
}
