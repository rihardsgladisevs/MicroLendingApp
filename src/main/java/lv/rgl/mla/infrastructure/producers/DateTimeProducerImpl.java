package lv.rgl.mla.infrastructure.producers;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@Component
public class DateTimeProducerImpl implements DateTimeProducer {
    @Override
    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
