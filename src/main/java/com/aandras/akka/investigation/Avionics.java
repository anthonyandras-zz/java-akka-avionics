package com.aandras.akka.investigation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * Created by anthonyandras on 10/15/16.
 */
@SpringBootApplication
public class Avionics {
    public static void main(String [] args) throws Exception {
        final ApplicationContext ctx = SpringApplication.run(Avionics.class, args);
        final String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for(String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
