/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.model.occi.platform.bigdata;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.cloud.automation.Model;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.MissingAttributesException;
import org.ow2.proactive.procci.model.occi.metamodel.constants.MetamodelAttributes;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.ResourceRendering;
import org.ow2.proactive.procci.model.occi.platform.Status;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataAttributes;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataIdentifiers;
import org.ow2.proactive.procci.model.occi.platform.bigdata.constants.BigDataKinds;
import org.ow2.proactive.procci.model.occi.platform.constants.PlatformAttributes;
import org.ow2.proactive.procci.service.occi.MixinService;


public class SwarmTest {

    @Mock
    private MixinService mixinService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void requiredConstructorTest() throws ClientException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest").build();
        assertThat(swarm.getHostIp()).matches("hostIpTest");
        assertThat(swarm.getMasterIp()).matches("masterIpTest");
        assertThat(swarm.getId()).isNotNull();
        assertThat(swarm.getKind()).isEqualTo(BigDataKinds.SWARM);
        assertThat(swarm.getAgentsIp()).isEmpty();
        assertThat(swarm.getStatus().isPresent()).isFalse();
        assertThat(swarm.getMachineName().isPresent()).isFalse();
        assertThat(swarm.getNetworkName().isPresent()).isFalse();
    }

    @Test
    public void allArgsConstructortest() throws ClientException {

        String agents = "agent1, agent2";
        Swarm swarm = new SwarmBuilder("hostIpTest2",
                                       "masterIpTest2",
                                       agents,
                                       Optional.of("machineNameTest"),
                                       Optional.of("networkNameTest")).status("active").title("titleTest").build();

        assertThat(swarm.getHostIp()).matches("hostIpTest2");
        assertThat(swarm.getMasterIp()).matches("masterIpTest2");
        assertThat(swarm.getAgentsIp()).containsExactly("agent1", "agent2");
        assertThat(swarm.getStatus().get()).isEqualTo(Status.ACTIVE);
        assertThat(swarm.getMachineName().get()).matches("machineNameTest");
        assertThat(swarm.getNetworkName().get()).matches("networkNameTest");
    }

    @Test
    public void cloudAutomationModelConstructorTest() throws ClientException {
        Model model = new Model.Builder(BigDataIdentifiers.SWARM_SCHEME, "create")
                                                                                  .addVariable(BigDataAttributes.AGENTS_IP_NAME,
                                                                                               "agentIp1, agentIp2")
                                                                                  .addVariable(BigDataAttributes.HOST_IP_NAME,
                                                                                               "hostIpTest3")
                                                                                  .addVariable(BigDataAttributes.MASTER_IP_NAME,
                                                                                               "masterIpTest3")
                                                                                  .addVariable(BigDataAttributes.NETWORK_NAME_NAME,
                                                                                               "my-net")
                                                                                  .addVariable(PlatformAttributes.STATUS_NAME,
                                                                                               "inactive")
                                                                                  .addVariable(MetamodelAttributes.ENTITY_TITLE_NAME,
                                                                                               "title")
                                                                                  .build();

        Swarm swarm = new SwarmBuilder(model).build();
        assertThat(swarm.getHostIp()).matches("hostIpTest3");
        assertThat(swarm.getMasterIp()).matches("masterIpTest3");
        assertThat(swarm.getAgentsIp()).containsExactly("agentIp1", "agentIp2");
        assertThat(swarm.getStatus().get()).isEqualTo(Status.INACTIVE);
        assertThat(swarm.getMachineName().isPresent()).isFalse();
        assertThat(swarm.getNetworkName().get()).matches("my-net");
    }

    @Test
    public void renderingConstructorTest() throws ClientException, IOException {

        when(mixinService.getMixinBuilder(BigDataAttributes.HOST_IP_NAME)).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder(BigDataAttributes.MASTER_IP_NAME)).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder(BigDataAttributes.AGENTS_IP_NAME)).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder(BigDataAttributes.MACHINE_NAME_NAME)).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder(PlatformAttributes.STATUS_NAME)).thenReturn(Optional.empty());
        when(mixinService.getMixinBuilder(MetamodelAttributes.SUMMARY_NAME)).thenReturn(Optional.empty());

        ResourceRendering rendering = new ResourceRendering.Builder(BigDataKinds.SWARM.getTitle(),
                                                                    "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29").addAttribute(BigDataAttributes.HOST_IP_NAME,
                                                                                                                                 "xx.xxx.xx.xxx")
                                                                                                                   .addAttribute(BigDataAttributes.MASTER_IP_NAME,
                                                                                                                                 "yy.yyy.yy.yyy")
                                                                                                                   .addAttribute(BigDataAttributes.AGENTS_IP_NAME,
                                                                                                                                 "aa.aaa.aa.aaa , bb.bbb.bb.bbb")
                                                                                                                   .addAttribute(BigDataAttributes.MACHINE_NAME_NAME,
                                                                                                                                 "machineTest")
                                                                                                                   .addAttribute(PlatformAttributes.STATUS_NAME,
                                                                                                                                 "inactive")
                                                                                                                   .addAttribute(MetamodelAttributes.SUMMARY_NAME,
                                                                                                                                 "summaryTest")
                                                                                                                   .build();

        Swarm swarm = new SwarmBuilder(mixinService, rendering).build();

        assertThat(swarm.getHostIp()).matches("xx.xxx.xx.xxx");
        assertThat(swarm.getMasterIp()).matches("yy.yyy.yy.yyy");
        assertThat(swarm.getAgentsIp()).containsExactly("aa.aaa.aa.aaa", "bb.bbb.bb.bbb");
        assertThat(swarm.getStatus().get()).isEqualTo(Status.INACTIVE);
        assertThat(swarm.getMachineName().get()).matches("machineTest");
        assertThat(swarm.getNetworkName().isPresent()).isFalse();
        assertThat(swarm.getTitle().isPresent()).isFalse();
        assertThat(swarm.getId()).isNotNull();
        assertThat(swarm.getSummary().get()).matches("summaryTest");

        ResourceRendering missingParameterRendering = new ResourceRendering.Builder(BigDataKinds.SWARM.getTitle(),
                                                                                    "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29").build();

        Exception ex = null;
        try {
            new SwarmBuilder(mixinService, missingParameterRendering).build();
        } catch (Exception e) {
            ex = e;
        }
        assertThat(ex).isInstanceOf(MissingAttributesException.class);

    }

    @Test
    public void getRenderingTest() throws ClientException {
        Swarm swarm = new SwarmBuilder("hostIpTest", "masterIpTest").addAgentIp(" agent1 ,agent2")
                                                                    .machineName("machineNameTest")
                                                                    .status("active")
                                                                    .title("titleTest")
                                                                    .build();

        ResourceRendering rendering = swarm.getRendering();
        assertThat(rendering.getLinks()).isEmpty();
        assertThat(rendering.getMixins()).isEmpty();
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.AGENTS_IP_NAME, "agent1,agent2");
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.HOST_IP_NAME, "hostIpTest");
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.MASTER_IP_NAME, "masterIpTest");
        assertThat(rendering.getAttributes()).containsEntry(BigDataAttributes.MACHINE_NAME_NAME, "machineNameTest");
        assertThat(rendering.getAttributes()).containsEntry(PlatformAttributes.STATUS_NAME, Status.ACTIVE.name());
        assertThat(rendering.getAttributes()).containsEntry(MetamodelAttributes.ENTITY_TITLE_NAME, "titleTest");

        assertThat(rendering.getId()).isNotNull();
        assertThat(rendering.getKind()).matches(BigDataKinds.SWARM.getTitle());

    }
}
