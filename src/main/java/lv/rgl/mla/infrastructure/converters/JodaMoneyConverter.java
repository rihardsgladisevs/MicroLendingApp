package lv.rgl.mla.infrastructure.converters;

import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
public class JodaMoneyConverter implements Converter<String, Money> {
    @Override
    public Money convert(String moneyString) {
        return Money.parse(moneyString);
    }
}
