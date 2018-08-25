package br.com.transpobrasil.bean;

import br.com.transpobrasil.helper.JSFHelper;
import br.com.transpobrasil.helper.LoggerHelper;
import br.com.transpobrasil.model.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import static br.com.transpobrasil.validator.IntervalValidation.anyNull;
import static br.com.transpobrasil.validator.IntervalValidation.minGreaterThanMax;

@ManagedBean
@RequestScoped
public class IntersectionBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntersectionBean.class);
    private static final String MSG_INTERSECTION = "Existe interseção entre as faixas 1 e 2.";
    private static final String MSG_NO_INTERSECTION = "Não há interseção entre as faixas 1 e 2.";

    private Interval intervalA;
    private Interval intervalB;

    @PostConstruct
    public void init() {
        intervalA = new Interval();
        intervalB = new Interval();
    }

    public void verify() {
        LOGGER.info("INIT verify intersection");
        try {
            isValidRange(intervalA);
            isValidRange(intervalB);

            if (intervalA.intersects(intervalB)) {
                JSFHelper.getInstance().showMessageSuccess(MSG_INTERSECTION);
            } else {
                JSFHelper.getInstance().showMessageWarning(MSG_NO_INTERSECTION);
            }

            LOGGER.info("INIT verify intersection");
        } catch (IllegalArgumentException iae) {
            LOGGER.error(LoggerHelper.ERROR, iae);
            JSFHelper.getInstance().showMessageError("Faixa com valores inválidos");
        }
    }

    private void isValidRange(Interval interval) {
        Boolean isInvalid = anyNull().or(minGreaterThanMax()).apply(interval);
        if (isInvalid) {
            LOGGER.error(LoggerHelper.ERROR, interval);
            throw new IllegalArgumentException("Interval invalid!");
        }
    }

    public Interval getIntervalA() {
        return intervalA;
    }

    public void setIntervalA(Interval intervalA) {
        this.intervalA = intervalA;
    }

    public Interval getIntervalB() {
        return intervalB;
    }

    public void setIntervalB(Interval intervalB) {
        this.intervalB = intervalB;
    }
}
