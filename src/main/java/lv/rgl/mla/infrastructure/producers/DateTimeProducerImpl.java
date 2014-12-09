package lv.rgl.mla.infrastructure.producers;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@Component
public class DateTimeProducerImpl implements DateTimeProducer {
    @Override
    public DateTime getCurrentDateTime() {
        return DateTime.now();
    }
}
