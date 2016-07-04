package dk.kyuff.pomlint.rules;

import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PomDependencyRuleTest {

    private PomDependencyRule rule;

    @Mock
    List<Dependency> dependencies;
    private MavenProject project;
    private String packaging;

    @Before
    public void setUp() throws Exception {
        rule = new PomDependencyRule();
        project = new MavenProjectStub() {
            @Override
            public String getPackaging() {
                return packaging;
            }

            @Override
            public List<Dependency> getDependencies() {
                return dependencies;
            }
        };
    }

    @Test
    public void pom_valid() throws Exception {
        // setup
        when(dependencies.size()).thenReturn(0);
        packaging = "pom";

        // execute
        boolean valid = rule.valid(project);

        // verify
        assertTrue(valid);
    }

    @Test
    public void pom_invalid_dependency() throws Exception {
        // setup
        when(dependencies.size()).thenReturn(1);
        packaging = "pom";

        // execute
        boolean valid = rule.valid(project);

        // verify
        assertFalse(valid);
    }


    @Test
    public void war_valid() throws Exception {
        // setup
        when(dependencies.size()).thenReturn(1);
        packaging = "war";

        // execute
        boolean valid = rule.valid(project);

        // verify
        assertTrue(valid);
    }
}