package running.core;

public interface Receivable<T> {
	void onReceive(T msg) throws Exception;
}
