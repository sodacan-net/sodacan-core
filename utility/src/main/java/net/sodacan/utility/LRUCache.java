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

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K,V> implements Cache<K,V>{
    private int size;
    private LinkedList<CacheElement<K,V>> cacheList;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(int size) {
        this.size = size;
        this.cacheList = new LinkedList<>();
    }

	@Override
    public void add(K key, V value) {
		lock.writeLock().lock();
		try {
	    	cacheList.addFirst(new CacheElement<K,V>(key,value));
	    	// Evict oldest if we're oversized
	    	if (size() > size) {
	    		cacheList.removeLast();
	    	}
		} finally {
			lock.writeLock().unlock();
		}
    }
	/**
	 * Find an item in the cache without moving it to the front of the list
	 * @param key
	 * @return
	 */
    protected V find(K key ) {
    	lock.readLock().lock();
    	try {
	    	for (CacheElement<K,V> ce : cacheList) {
	    		if (ce.getKey().equals(key)) {
	    			return ce.getValue();
	    		}
	    	}
	    	return null;
    	} finally {
    		lock.readLock().unlock();
    	}
    }

    /**
     * Get an item from the list, if any, and move the found item to the front of the list
     * signifying that it is no longer the least recently used item. 
     */
	@Override
	public V get(K key) {
    	lock.writeLock().lock();
    	try {
	    	for (CacheElement<K,V> ce : cacheList) {
	    		if (ce.getKey().equals(key)) {
	    			cacheList.remove(ce);
	    			cacheList.addFirst(ce);
	    			return ce.getValue();
	    		}
	    	}
			return null;
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public int size() {
		return cacheList.size();
	}

	@Override
	public boolean isEmpty() {
		return (size()==0);
	}

	@Override
	public void clear() {
		cacheList.removeAll(cacheList);
		
	}
}
