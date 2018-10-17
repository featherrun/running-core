package running.core;

public interface Entry<K, V> {
	void entry(K k, V v);
}