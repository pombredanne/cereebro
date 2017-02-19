package io.cereebro.server.graph.sigma;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.System;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link Graph} unit tests.
 * 
 * @author michaeltecourt
 */
public class GraphTest {

    @Test
    public void system() {
        // @formatter:off
        ComponentRelationships gambit = ComponentRelationships.builder()
                .component(Component.of("gambit", "superhero"))
                .addDependency(Dependency.on(Component.of("rogue", "superhero")))
                .addConsumer(Consumer.by(Component.of("angel", "superhero")))
                .build();
        
        ComponentRelationships rogue = ComponentRelationships.builder()
                .component(Component.of("rogue", "superhero"))
                .addConsumer(Consumer.by(Component.of("gambit", "superhero")))
                .build();
        
        ComponentRelationships angel = ComponentRelationships.builder()
                .component(Component.of("angel", "superhero"))
                .addDependency(Dependency.on(Component.of("gambit", "superhero")))
                .build();
        // @formatter:on

        System system = System.of("xmen", gambit, angel, rogue);
        Graph graph = Graph.of(system);
        Assert.assertEquals(3, graph.getNodes().size());
        Edge gambitToRogue = Edge.create("gambit-to-rogue", "gambit", "rogue");
        Edge angelToGambit = Edge.create("angel-to-gambit", "angel", "gambit");
        Set<Edge> expectedEdges = new HashSet<>(Arrays.asList(gambitToRogue, angelToGambit));
        Assert.assertEquals(expectedEdges, graph.getEdges());
    }

    @Test
    public void verifyEqualsAndHashCode() {
        EqualsVerifier.forClass(Graph.class).suppress(Warning.STRICT_INHERITANCE).verify();
    }

    @Test
    public void testToString() {
        Assert.assertNotNull(Graph.create(new HashSet<>(), new HashSet<>()));
    }

}