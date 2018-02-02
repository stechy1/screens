package cz.stechy.screens.simple.widget.builder;

import cz.stechy.screens.base.WidgetBuilderProvider;
import cz.stechy.screens.simple.widget.CustomWidget;

public class CustomWidgetBuilder implements WidgetBuilderProvider<CustomWidget> {

    private final String title = "default value";

    @Override
    public boolean isBuilderFor(Class<?> clazz) {
        return CustomWidget.class.isAssignableFrom(clazz);
        //return CustomWidget.class.getName().equals(clazz.getName());
    }

    @Override
    public CustomWidget build() {
        return new CustomWidget(title);
    }
}
