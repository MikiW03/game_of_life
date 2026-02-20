package gui.util;

import java.awt.Color;

public class ThreadColorGenerator {
    public static Color generateThreadColor(int threadId, int totalThreads) {
        float hue = ((float) threadId / totalThreads) * 1.2f;
        Color baseColor = Color.getHSBColor(hue, 0.3f, 1.0f);
        return new Color(
            baseColor.getRed(),
            baseColor.getGreen(),
            baseColor.getBlue(),
            70
        );
    }
}