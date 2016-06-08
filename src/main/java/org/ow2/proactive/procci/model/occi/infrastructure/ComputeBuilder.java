package org.ow2.proactive.procci.model.occi.infrastructure;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ow2.proactive.procci.model.occi.infrastructure.constants.InfrastructureKinds;
import org.ow2.proactive.procci.model.occi.infrastructure.state.ComputeState;
import org.ow2.proactive.procci.model.occi.metamodel.Link;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mael on 02/06/16.
 */
@EqualsAndHashCode
@ToString
public class ComputeBuilder {

    private final String url;
    private String title;
    private String summary;
    private Compute.Architecture architecture;
    private Integer cores;
    private Integer share;
    private Float memory; // in Gigabytes
    private String hostname;
    private ComputeState state;
    private List<Link> links;
    private List<Mixin> mixins;

    public ComputeBuilder(String url) {
        this.url = url;
        title = "";
        summary = "";
        architecture = null;
        cores = null;
        share = null;
        hostname = "";
        memory = null;
        state = null;
        mixins = new ArrayList<>();
        links = new ArrayList<>();
    }

    public ComputeBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ComputeBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }


    public ComputeBuilder addMixin(Mixin mixin) {
        this.mixins.add(mixin);
        return this;
    }

    public ComputeBuilder addLink(Link link) {
        this.links.add(link);
        return this;
    }

    public ComputeBuilder architecture(Compute.Architecture architecture) {
        this.architecture = architecture;
        return this;
    }

    public ComputeBuilder cores(Integer cores) {
        this.cores = cores;
        return this;
    }

    public ComputeBuilder share(Integer share) {
        this.share = share;
        return this;
    }

    public ComputeBuilder hostame(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public ComputeBuilder memory(Float memory) {
        this.memory = memory;
        return this;
    }

    public ComputeBuilder state(ComputeState state) {
        this.state = state;
        return this;
    }

    public Compute build() {
        return new Compute(url, InfrastructureKinds.COMPUTE, title, new ArrayList<>(), summary, new ArrayList<>(), architecture,
                cores, share, hostname, memory, state);
    }

}