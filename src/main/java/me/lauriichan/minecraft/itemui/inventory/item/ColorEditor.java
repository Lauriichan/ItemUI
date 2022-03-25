package me.lauriichan.minecraft.itemui.inventory.item;

import java.util.Iterator;
import java.util.List;

import me.lauriichan.minecraft.itemui.util.ColorList;

@SuppressWarnings("rawtypes")
abstract class ColorEditor<E extends ColorEditor> {

    final ItemEditor editor;
    final ColorList content = new ColorList();

    public ColorEditor(final ItemEditor editor) {
        this.editor = editor;
        onInit();
    }

    abstract void onInit();

    @SuppressWarnings("unchecked")
    private E me() {
        return (E) this;
    }

    public String color(final int index) {
        return content.get(index);
    }

    public String plain(final int index) {
        return content.getPlain(index);
    }

    public String stripped(final int index) {
        return content.getStripped(index);
    }

    public String[] color(final int start, final int length) {
        final int size = content.size();
        if (start >= size) {
            throw new IndexOutOfBoundsException("start can't be bigger than size (" + Math.abs(size - start) + ")");
        }
        if (length > size) {
            throw new IndexOutOfBoundsException("length can't be bigger than size (" + Math.abs(size - length) + ")");
        }
        final String[] output = new String[length];
        for (int index = start; index < length; index++) {
            output[index] = color(index);
        }
        return output;
    }

    public String[] plain(final int start, final int length) {
        final int size = content.size();
        if (start >= size) {
            throw new IndexOutOfBoundsException("start can't be bigger than size (" + Math.abs(size - start) + ")");
        }
        if (length > size) {
            throw new IndexOutOfBoundsException("length can't be bigger than size (" + Math.abs(size - length) + ")");
        }
        final String[] output = new String[length];
        for (int index = start; index < length; index++) {
            output[index] = plain(index);
        }
        return output;
    }

    public String[] stripped(final int start, final int length) {
        final int size = content.size();
        if (start >= size) {
            throw new IndexOutOfBoundsException("start can't be bigger than size (" + Math.abs(size - start) + ")");
        }
        if (length > size) {
            throw new IndexOutOfBoundsException("length can't be bigger than size (" + Math.abs(size - length) + ")");
        }
        final String[] output = new String[length];
        for (int index = start; index < length; index++) {
            output[index] = stripped(index);
        }
        return output;
    }

    public E set(final int index, final String line) {
        content.set(index, line);
        return me();
    }

    public E set(final String... lines) {
        content.clear();
        return add(lines);
    }

    public E set(final List<String> lines) {
        content.clear();
        return add(lines);
    }

    public E set(final Iterable<String> lines) {
        return lines == null ? clear() : set(lines.iterator());
    }

    public E set(final Iterator<String> lines) {
        content.clear();
        return add(lines);
    }

    public E add(final String line) {
        content.add(line);
        return me();
    }

    public E add(final int index, final String line) {
        content.add(index, line);
        return me();
    }

    public E add(final String... lines) {
        if (lines == null) {
            return me();
        }
        for (int index = 0; index < lines.length; index++) {
            content.add(lines[index]);
        }
        return me();
    }

    public E add(final List<String> lines) {
        if (lines == null) {
            return me();
        }
        final int size = lines.size();
        for (int index = 0; index < size; index++) {
            content.add(lines.get(index));
        }
        return me();
    }

    public E add(final Iterable<String> lines) {
        return lines == null ? me() : add(lines.iterator());
    }

    public E add(final Iterator<String> lines) {
        if (lines == null) {
            return me();
        }
        while (lines.hasNext()) {
            content.add(lines.next());
        }
        return me();
    }

    public String removeGet(final int index) {
        return content.remove(index);
    }

    public E remove(final int index) {
        content.remove(index);
        return me();
    }

    public E remove(final String line) {
        content.remove(line);
        return me();
    }

    public E remove(final String... lines) {
        if (lines == null) {
            return me();
        }
        for (int index = 0; index < lines.length; index++) {
            content.remove(lines[index]);
        }
        return me();
    }

    public E remove(final List<String> lines) {
        if (lines == null) {
            return me();
        }
        final int size = lines.size();
        for (int index = 0; index < size; index++) {
            content.remove(lines.get(size));
        }
        return me();
    }

    public E remove(final Iterable<String> lines) {
        return lines == null ? me() : remove(lines.iterator());
    }

    public E remove(final Iterator<String> lines) {
        if (lines == null) {
            return me();
        }
        while (lines.hasNext()) {
            content.remove(lines.next());
        }
        return me();
    }

    public int length() {
        return content.size();
    }

    public E clear() {
        content.clear();
        return me();
    }

    public abstract ItemEditor apply();

    public ItemEditor getHandle() {
        return editor;
    }

}
