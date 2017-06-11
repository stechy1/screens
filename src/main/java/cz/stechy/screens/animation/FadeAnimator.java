package cz.stechy.screens.animation;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Jednoduchá animace okna, která mění opacitu.
 * FadeIn: 0 -> 1
 * FadeOut: 1 -> 0
 */
public class FadeAnimator implements IScreenAnimator {

    // Výchozí doba trvání animace
    private static final Duration DEFAULT_DURATION = Duration.millis(650);

    // Doba trvání animace
    private final Duration duration;

    /**
     * Vytvoří nový animátor s výchozí dobou trvání
     */
    public FadeAnimator() {
        this(DEFAULT_DURATION);
    }

    /**
     * Vytvoří nový animátor s nastavitelnou dobou trvání
     *
     * @param duration Doba trvání animace
     */
    public FadeAnimator(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Transition getShowAnimation(Node node) {
        final FadeTransition fadeIn = new FadeTransition(duration, node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        return fadeIn;
    }

    @Override
    public Transition getHideAnimation(Node node) {
        final FadeTransition fadeOut = new FadeTransition(duration, node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        return fadeOut;
    }
}
