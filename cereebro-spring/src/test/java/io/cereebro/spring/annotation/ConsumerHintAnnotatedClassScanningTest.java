package io.cereebro.spring.annotation;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Relationship;
import io.cereebro.spring.annotation.test.scan.ConsumerHintAnnotatedClass;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class ConsumerHintAnnotatedClassScanningTest {

    @Autowired
    private ConsumerHintAnnotationRelationshipDetector detector;

    @Test
    public void test() {
        Set<Relationship> result = detector.detect();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Relationship actual = result.iterator().next();
        Consumer expected = Consumer.by(Component.of("wolverine", "xmen"));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Configuration
    @ComponentScan(basePackageClasses = ConsumerHintAnnotatedClass.class)
    public static class ComponentScanningTestConfig {

        @Bean
        public ConsumerHintAnnotationRelationshipDetector detector() {
            return new ConsumerHintAnnotationRelationshipDetector();
        }

    }

}