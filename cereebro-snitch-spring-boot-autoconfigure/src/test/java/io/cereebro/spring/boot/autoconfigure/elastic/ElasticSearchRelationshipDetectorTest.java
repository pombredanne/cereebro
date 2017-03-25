/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cereebro.spring.boot.autoconfigure.elastic;

import org.assertj.core.api.Assertions;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.spring.boot.autoconfigure.elastic.ElasticSearchRelationshipDetectorTest.ESTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESTestApplication.class)
public class ElasticSearchRelationshipDetectorTest {

    @Autowired
    private ElasticSearchRelationshipDetector detector;

    @Test
    public void testElasticSearchDetector() {
        Assertions.assertThat(detector.detect())
                .contains(Dependency.on(Component.of("mycluster", ComponentType.ELASTIC_SEARCH)));
    }

    @SpringBootApplication
    public static class ESTestApplication {

        @Bean
        public Client elasticSearchClient() {
            Client mockClient = Mockito.mock(Client.class);
            Settings settings = Settings.builder().put("cluster.name", "mycluster").build();
            Mockito.when(mockClient.settings()).thenReturn(settings);
            return mockClient;
        }

    }

}
