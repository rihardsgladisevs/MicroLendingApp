package lv.rgl.mla.acceptance;

import cucumber.api.java.Before;
import lv.rgl.mla.Application;
import org.springframework.boot.SpringApplication;

/**
 * Created by rihards.gladisevs on 09.12.2014..
 */
public class HernessDefs {
    @Before("@spring-start")
    public void before() {
        SpringApplication.run(Application.class, new String[]{});
    }
}
