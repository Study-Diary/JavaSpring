package com.seikim.spring.solution;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class ConcurrentWeakReferenceMap<K, V> {
	private final ConcurrentHashMap<K, WeakReference<V>> map = new ConcurrentHashMap<>();

	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return map.computeIfAbsent(key, k -> new WeakReference<>(mappingFunction.apply(k))).get();
	}

	public void cleanUp() {
		map.entrySet().removeIf(entry -> entry.getValue().get() == null);
	}
}
