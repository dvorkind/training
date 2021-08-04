package by.dvorkin.strings.entities;

import java.util.Iterator;
import java.util.Stack;

public class TextIterator implements Iterator<TextComponent> {
    private Stack<Iterator<TextComponent>> stack = new Stack<>();

    public TextIterator(Iterator<TextComponent> iterator) {
        stack.push(iterator);
    }

    public TextComponent next() {
        if (hasNext()) {
            Iterator<TextComponent> iterator = stack.peek();
            TextComponent textComponent = iterator.next();
            stack.push(textComponent.createIterator());
            return textComponent;
        } else {
            return null;
        }
    }

    public boolean hasNext() {
        if (stack.empty()) {
            return false;
        } else {
            Iterator<TextComponent> iterator = stack.peek();
            if (!iterator.hasNext()) {
                stack.pop();
                return hasNext();
            } else {
                return true;
            }
        }
    }
}
