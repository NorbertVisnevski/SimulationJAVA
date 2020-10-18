package com.NV.simulation.formaters;

import javafx.scene.control.TextFormatter;
import java.text.DecimalFormat;
import java.text.ParsePosition;

public class NumberTextFormatter {

    private static DecimalFormat format = new DecimalFormat("#");

    public static final TextFormatter getFormatter()
    {
        return new TextFormatter<Object>(change -> {
            if (change.getControlNewText().isEmpty()) {
                return change;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(change.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < change.getControlNewText().length()) {
                return null;
            } else {
                return change;
            }
        });
    }

}

