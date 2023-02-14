/*
 * Copyright 2023 John M Churin
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.net.sodacan.api.utility;

import org.junit.Test;

import net.sodacan.module.ModuleMethods;

public class TestModuleMethods {

	@Test
	public void testFullName() {
		String parsed[] = ModuleMethods.parseFullModuleName("module");
		assert(parsed.length==1);
		assert(parsed[0].equals("module"));
	}
	@Test
	public void testFullNameWithInstance() {
		String parsed[] = ModuleMethods.parseFullModuleName("module[instance]");
		assert(parsed.length==2);
		assert(parsed[0].equals("module"));
		assert(parsed[1].equals("instance"));
	}

}
