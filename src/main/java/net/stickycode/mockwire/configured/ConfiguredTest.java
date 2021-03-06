/**
 * Copyright (c) 2010 RedEngine Ltd, http://www.redengine.co.nz. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package net.stickycode.mockwire.configured;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.junit.Test;

import net.stickycode.mockwire.InvalidConfigurationException;
import net.stickycode.mockwire.Mockwire;
import net.stickycode.mockwire.MockwireConfigured;
import net.stickycode.mockwire.UnderTest;

public class ConfiguredTest {

  @MockwireConfigured("configuredObject.a=b")
  public class StringConfigured {

    @UnderTest
    ConfiguredObject configured;
  }

  @Test
  public void inlineConfigured() {
    StringConfigured testInstance = new StringConfigured();
    Mockwire.isolate(testInstance);
    assertThat(testInstance.configured.a).isEqualTo("b");
  }

  @MockwireConfigured
  public class PropertiesConfigured {

    @UnderTest
    ConfiguredObject configured;
  }

  @Test
  public void propertiesConfigured() {
    PropertiesConfigured testInstance = new PropertiesConfigured();
    Mockwire.isolate(testInstance);
    assertThat(testInstance.configured.a).isEqualTo("bfromfile");
  }

  @MockwireConfigured
  public class InlineConfigured {

    @UnderTest("a=inline")
    ConfiguredObject configured;
  }

  @Test
  public void testObjectConfigured() {
    InlineConfigured testInstance = new InlineConfigured();
    Mockwire.isolate(testInstance);
    assertThat(testInstance.configured.a).isEqualTo("inline");
  }

  public class InlineConfiguredNeedsFeature {

    @UnderTest("a=inline")
    ConfiguredObject configured;
  }

  @Test(expected=MockwireConfiguredIsRequiredToTestConfiguredCodeException.class)
  public void checkInlineConfigurationExcepts() {
    InlineConfiguredNeedsFeature testInstance = new InlineConfiguredNeedsFeature();
    Mockwire.isolate(testInstance);
  }

  @MockwireConfigured
  public class InlineConfiguredNeedsBroken {

    @UnderTest("broken")
    ConfiguredObject configured;
  }

  @Test(expected=InvalidConfigurationException.class)
  public void checkInlineConfigurationBrokenExcepts() {
    InlineConfiguredNeedsBroken testInstance = new InlineConfiguredNeedsBroken();
    Mockwire.isolate(testInstance);
  }


  @MockwireConfigured("configuredObject.a=b")
  public class SuperclassConfigured {

  }

  public class SubclassConfigured {
    @UnderTest
    ConfiguredObject configured;
  }

  @Test
  public void testSubclassIsConfiguredWithSuperclassConfiguration() {
    SubclassConfigured testInstance = new SubclassConfigured();
    Mockwire.isolate(testInstance);

    assertThat(testInstance.configured.a).isEqualTo("b");
  }
}
