package org.ow2.proactive.procci.model.occi.metamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.ow2.proactive.procci.model.exception.SyntaxException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.constants.Kinds;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 2/25/16.
 */
public class ResourceTest {


    @Test
    public void constructorTest() {

        Kind kind = Kinds.RESOURCE;
        List<Mixin> mixins = new ArrayList<>();
        List<Link> links = new ArrayList<>();
        mixins.add(new Mixin("http://schemas.ogf.org/occi/metamodel#mixin", "mixin", "mixin",
                new HashSet<Attribute>(),
                new ArrayList<Action>(), new ArrayList<Mixin>(), new ArrayList<Kind>(),
                new ArrayList<Entity>()));
        Resource resource = new Resource(Optional.of("url"), kind, Optional.of("titleTest"), mixins,
                Optional.of("summaryTest"), links);

        assertThat(resource.getSummary().get()).isEqualTo("summaryTest");
        assertThat(resource.getLinks()).isEqualTo(links);
        assertThat(resource.getMixins()).isEqualTo(mixins);
        assertThat(resource.getKind()).isEqualTo(kind);
        assertThat(resource.getId().toString()).startsWith("url");
    }

    @Test
    public void builderTest() {
        Compute compute = new ComputeBuilder().url("compute").build();
        try {
            Link link = new Link.Builder(compute, "target").url("link").build();
            Attribute mixinAttribute = new Attribute.Builder("attribute").type(Type.OBJECT).mutable(
                    false).required(true).build();
            Mixin mixin = new MixinBuilder("schemeTest", "termTest")
                    .addAttribute(mixinAttribute)
                    .build();
            Resource resource = new Resource.Builder()
                    .url("resource")
                    .summary("summary")
                    .title("title")
                    .addLink(link)
                    .addMixin(mixin)
                    .build();
            assertThat(resource.getSummary().get()).isEqualTo("summary");
            assertThat(resource.getTitle().get()).isEqualTo("title");
            assertThat(resource.getId().toString()).contains("resource");
            assertThat(resource.getLinks()).containsExactly(link);
            assertThat(resource.getMixins()).contains(mixin);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
    }
}
