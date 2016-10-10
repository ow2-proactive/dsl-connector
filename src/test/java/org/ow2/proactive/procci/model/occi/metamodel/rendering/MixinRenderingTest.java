package org.ow2.proactive.procci.model.occi.metamodel.rendering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by mael on 10/10/16.
 */
public class MixinRenderingTest {

    @Test
    public void getJsonTest() {

        MixinRendering rendering = MixinRendering.builder().scheme("schemeTest").term("termTest").build();
        try {
            String renderingJson = MixinRendering.convertStringFromMixin(rendering);
            assertThat(renderingJson).isNotEmpty();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @Test
    public void convertMixinFromStringTest() {

        List<String> depends = new ArrayList<>();
        List<String> applies = new ArrayList<>();
        Map<String, AttributeRendering> attributes = new HashMap<>();

        AttributeRendering attributeRendering = new AttributeRendering();

        depends.add("dependMixin");
        applies.add("compute");
        attributes.put("attributeTest", attributeRendering);


        MixinRendering rendering = null;

        try {
            rendering = MixinRendering.convertMixinFromString(
                    MixinRendering.convertStringFromMixin(
                            MixinRendering.builder()
                                    .scheme("schemeTest")
                                    .term("termTest")
                                    .title("titleTest")
                                    .depends(depends)
                                    .applies(applies)
                                    .attributes(attributes)
                                    .build()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(rendering.getScheme()).matches("schemeTest");
        assertThat(rendering.getTerm()).matches("termTest");
        assertThat(rendering.getTitle()).matches("titleTest");
        assertThat(rendering.getDepends()).contains("dependMixin");
        assertThat(rendering.getApplies()).contains("compute");
        assertThat(rendering.getAttributes()).containsKey("attributeTest");


    }
}
