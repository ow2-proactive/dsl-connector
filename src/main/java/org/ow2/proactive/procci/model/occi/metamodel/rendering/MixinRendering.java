package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by mael on 22/09/16.
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(builderClassName = "Builder")
public class MixinRendering {


    private String term;
    private String scheme;
    private String title;
    private Map<String, AttributeRendering> attributes;
    private List<String> actions;
    private List<String> depends;
    private List<String> applies;
    private String location;

    public static MixinRendering convertMixinFromString(String mixinRendering) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(mixinRendering, MixinRendering.class);
    }

    public static String convertStringFromMixin(MixinRendering mixinRendering) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mixinRendering);
    }
}
