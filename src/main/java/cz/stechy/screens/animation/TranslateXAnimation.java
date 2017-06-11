package cz.stechy.screens.animation;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 */
public class TranslateXAnimation implements IScreenAnimator {

    // Výchozí doba trvání animace
    private static final Duration DEFAULT_DURATION = Duration.millis(650);

    // Doba trvání animace
    private final Duration duration;

    /**
     * Vytvoří nový animátor s výchozí dobou trvání
     */
    public TranslateXAnimation() {
        this(DEFAULT_DURATION);
    }

    /**
     * Vytvoří nový animátor s nastavitelnou dobou trvání
     *
     * @param duration Doba trvání animace
     */
    public TranslateXAnimation(Duration duration) {
        this.duration = duration;
    }

    @Override
    public Transition getShowAnimation(Node node) {
        final TranslateTransition sladeIn = new TranslateTransition(duration, node);
        sladeIn.setFromX(-100f);
        sladeIn.setToX(0);

        final FadeTransition fadeIn = new FadeTransition(duration, node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        final ParallelTransition parallelTransition = new ParallelTransition(sladeIn, fadeIn);

        return parallelTransition;
    }

    @Override
    public Transition getHideAnimation(Node node) {
        final TranslateTransition sladeOut = new TranslateTransition(duration, node);
        sladeOut.setFromX(0);
        sladeOut.setToX(-100);

        final FadeTransition fadeOut = new FadeTransition(duration, node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        final ParallelTransition parallelTransition = new ParallelTransition(sladeOut, fadeOut);

        return parallelTransition;
    }
}
