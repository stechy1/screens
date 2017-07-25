package cz.stechy.screens;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Třída představující jednu notifikaci
 */
public final class Notification {

    public final String message;
    public final String actionText;
    public final Length length;
    public final boolean persistent;
    public final EventHandler<? super MouseEvent> actionHandler;

    public Notification(String message) {
        this(message, Length.SHORT);
    }

    public Notification(String message, Length length) {
        this.message = message;
        this.length = length;
        this.actionText = null;
        this.persistent = false;
        this.actionHandler = null;
    }

    public Notification(String message, String actionText,
        EventHandler<? super MouseEvent> actionHandler) {
        this.message = message;
        this.actionText = actionText;
        this.actionHandler = actionHandler;
        this.length = Length.INFINITE;
        this.persistent = true;
    }

    public enum Length {
        SHORT, LONG, INFINITE
    }
}
