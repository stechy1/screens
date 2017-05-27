package cz.stechy.screens;

import javafx.util.Duration;

/**
 * Třída představující jednu notifikaci
 */
public final class Notification {

    public final String text;
    public final Duration duration;

    public Notification(String text, Duration duration) {
        this.text = text;
        this.duration = duration;
    }

    public enum Length {
        SHORT(1), LONG(3);

        private final Duration duration;

        Length(int seconds) {
            this.duration = Duration.seconds(seconds);
        }

        public Duration getDuration() {
            return duration;
        }
    }
}
