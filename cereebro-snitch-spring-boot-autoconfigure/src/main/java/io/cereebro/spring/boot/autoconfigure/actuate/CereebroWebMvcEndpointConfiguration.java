/*
 * Copyright © 2009 the original authors (http://cereebro.io)
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
package io.cereebro.spring.boot.autoconfigure.actuate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.cereebro.core.Component;
import io.cereebro.core.CompositeRelationshipDetector;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.CereebroProperties;

@ManagementContextConfiguration
@EnableConfigurationProperties({ CereebroProperties.class })
@ConditionalOnClass(MvcEndpoint.class)
public class CereebroWebMvcEndpointConfiguration {

    @Autowired
    private CereebroProperties cereebroProperties;

    @Bean
    @ConditionalOnEnabledEndpoint("cereebro")
    public CereebroSnitchMvcEndpoint snitchMvcEndpoint(List<RelationshipDetector> detectors) {
        Component applicationComponent = cereebroProperties.getApplication().getComponent().toComponent();
        return new CereebroSnitchMvcEndpoint(applicationComponent, new CompositeRelationshipDetector(detectors));
    }

}
