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

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TestStream {
	
//	@Test
//	public void test() {
//		Supplier<String> supplier = new ModuleSupplier();
//		Stream.generate(supplier)
//		    .limit(5)
//		    .forEach(new ModuleConsumer());
//	}
//
//	@Test
//	public void testTakeWhile() {
//		Consumer<String> consumer = new ModuleConsumer();
//		Supplier<String> supplier = new ModuleSupplier();
//		Stream.generate(supplier)
//	    	.takeWhile( x -> x.startsWith("a"))
//	    	.forEach(consumer);
//		System.out.println("******************");
//		Stream.generate(supplier)
//    	.takeWhile( x -> x.startsWith("b"))
//    	.forEach(consumer);
//		 
//	}
//
//	@Test
//	public void testToList() {
//		Consumer<String> consumer = new ModuleConsumer();
//		Supplier<String> supplier = new ModuleSupplier();
//		List<String> asList = Stream.generate(supplier).collect(Collectors.toList());
//		System.out.println("The list: " + asList);
//		 
//	}
//
}
