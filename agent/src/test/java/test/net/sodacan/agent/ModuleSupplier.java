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
package test.net.sodacan.agent;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

public class ModuleSupplier  implements Supplier<String>, Closeable  {
	int mult = 0;
	int count = 0;
	BufferedReader input;
	
	public ModuleSupplier() {
		try {
			this.input = new BufferedReader(new FileReader("x"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public String get() {
		try {
			String line =  input.readLine();
			return line;
		} catch (IOException e) {
		}
		return null;
	}


	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}


}
