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
package net.sodacan.utility;

import java.util.Optional;

public interface Cache<K, V> {
	/**
	 * Add a new item to the cache. Return true if this replaces an item with the same key
	 * @param key
	 * @param value
	 * @return
	 */
    public void add(K key, V value);
    
    public V get(K key);
    public int size();
    public boolean isEmpty();
    public void clear();
}
