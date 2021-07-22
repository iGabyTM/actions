package me.gabytm.util.actions.components.implementations;

import com.google.common.primitives.Ints;
import com.udojava.evalex.Expression;
import me.gabytm.util.actions.components.Component;
import me.gabytm.util.actions.placeholders.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathComponent<T> extends Component<T, BigDecimal> {

    public static final String ID = "math";

    private final int scale;
    private final String stringValue;

    public MathComponent(@NotNull String stringValue, @NotNull PlaceholderManager placeholderManager) {
        super(stringValue, placeholderManager);
        final String[] parts = stringValue.split(",", 2);

        if (parts.length == 2) {
            final Integer scale = Ints.tryParse(parts[0]);

            if (scale == null) {
                this.scale = -1;
                this.stringValue = stringValue;
            } else {
                this.scale = scale;
                this.stringValue = parts[1];
            }
        } else {
            this.scale = -1;
            this.stringValue = stringValue;
        }
    }

    @Override
    public @NotNull BigDecimal parse(T t) {
        final BigDecimal result = new Expression(placeholderManager.replace(t, stringValue)).eval(false);
        return (scale < 0) ? result : result.setScale(scale, RoundingMode.HALF_UP);
    }

}
