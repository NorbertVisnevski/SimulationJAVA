package com.NV.simulation.formaters;

import javafx.scene.control.TextFormatter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class NumberTextFormatter {

    private static final DecimalFormat format = new DecimalFormat("#");

    public final TextFormatter getFormatter()
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

