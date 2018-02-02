package cz.stechy.screens;

import cz.stechy.screens.base.WidgetBuilderProvider;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

final class CustomWidgetBuilder implements BuilderFactory {

    // region Variables

    private final List<WidgetBuilderProvider> customBuilderProviders = new ArrayList<>();

    // endregion

    @SuppressWarnings("unchecked")
    @Override
    public Builder<?> getBuilder(Class<?> type) {
        for (WidgetBuilderProvider customBuilderProvider : customBuilderProviders) {
            if (customBuilderProvider.isBuilderFor(type)) {
                return customBuilderProvider;
            }
        }

        return null;
    }

    // region Public methods

    /**
     * Přidá stavitele widgetu
     *
     * @param widgetBuilder {@link WidgetBuilderProvider}
     */
    void addWidgetBuilderProvider(WidgetBuilderProvider widgetBuilder) {
        customBuilderProviders.add(widgetBuilder);
    }

    /**
     * Odebere stavitele widgetu
     *
     * @param widgetBuilder {@link WidgetBuilderProvider}
     */
    void removeWidgetBuilderProvider(WidgetBuilderProvider widgetBuilder) {
        customBuilderProviders.remove(widgetBuilder);
    }

    // endregion
}
