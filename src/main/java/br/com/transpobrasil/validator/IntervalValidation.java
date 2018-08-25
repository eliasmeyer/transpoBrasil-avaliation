package br.com.transpobrasil.validator;

import br.com.transpobrasil.model.Interval;

import java.util.Objects;
import java.util.function.Function;

public interface IntervalValidation extends Function<Interval, Boolean> {

    static IntervalValidation anyNull() {
        return (interval) -> Objects.isNull(interval) ||
                Objects.isNull(interval.getMin()) ||
                Objects.isNull(interval.getMax());
    }

    static IntervalValidation minGreaterThanMax() {
        return (interval) -> interval.getMin() > interval.getMax();
    }

    default IntervalValidation or(IntervalValidation other) {
        return user -> this.apply(user) || other.apply(user);
    }
}
