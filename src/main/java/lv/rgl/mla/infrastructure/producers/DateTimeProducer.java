package lv.rgl.mla.infrastructure.producers;

import java.time.LocalDateTime;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
public interface DateTimeProducer {
    LocalDateTime getCurrentDateTime();
}
