package cz.stechy.screens.base;

import javafx.util.Builder;

public interface WidgetBuilderProvider<T> extends Builder<T> {

    boolean isBuilderFor(Class<?> clazz);

}
