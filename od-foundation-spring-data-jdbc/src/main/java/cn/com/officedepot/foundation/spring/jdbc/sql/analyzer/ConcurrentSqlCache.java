package cn.com.officedepot.foundation.spring.jdbc.sql.analyzer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ConcurrentSqlCache<K, V> extends LinkedHashMap<K, V> implements ConcurrentMap<K, V> {

	private static final long serialVersionUID = 1L;

	private static final Lock lock = new ReentrantLock();

	@Override
	public void clear() {
		try {
			lock.lock();
			super.clear();

		} finally {
			lock.unlock();
		}
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		try {
			lock.lock();
			return super.removeEldestEntry(eldest);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		try {
			lock.lock();
			super.replaceAll(function);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V put(K key, V value) {
		try {
			lock.lock();
			return super.put(key, value);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		try {
			lock.lock();
			super.putAll(m);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V remove(Object key) {
		try {
			lock.lock();
			return super.remove(key);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V putIfAbsent(K key, V value) {
		try {
			lock.lock();
			return super.putIfAbsent(key, value);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean remove(Object key, Object value) {
		try {
			lock.lock();
			return super.remove(key, value);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		try {
			lock.lock();
			return super.replace(key, oldValue, newValue);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V replace(K key, V value) {
		try {
			lock.lock();
			return super.replace(key, value);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		try {
			lock.lock();
			return super.computeIfAbsent(key, mappingFunction);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		try {
			lock.lock();
			return super.computeIfPresent(key, remappingFunction);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		try {
			lock.lock();
			return super.compute(key, remappingFunction);

		} finally {
			lock.unlock();
		}
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		try {
			lock.lock();
			return super.merge(key, value, remappingFunction);

		} finally {
			lock.unlock();
		}
	}

}
