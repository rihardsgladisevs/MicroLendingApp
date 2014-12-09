package lv.rgl.mla.infrastructure.configuration;

import lv.rgl.mla.infrastructure.converters.JodaMoneyConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@Configuration
public class ConversionConfiguration {
    @Bean(name = "conversionService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }

    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<Converter>();
        converters.add(new JodaMoneyConverter());

        return converters;
    }
}
