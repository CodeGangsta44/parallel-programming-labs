package edu.kpi.ip71.dovhopoliuk.util.data;

import java.util.Objects;
import java.util.Optional;

public class Element {

    private ElementType type;
    private int index;
    private int value;

    public Element(ElementType type, int index, int value) {

        this.type = type;
        this.index = index;
        this.value = value;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {

        return this == o || Optional.of(o)
                .filter(Element.class::isInstance)
                .map(Element.class::cast)

                .map(element -> index == element.index
                        && value == element.value
                        && type == element.type)

                .orElse(Boolean.FALSE);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, index, value);
    }

    @Override
    public String toString() {
        return "Element{" +
                "type=" + type +
                ", index=" + index +
                ", value=" + value +
                '}';
    }
}
