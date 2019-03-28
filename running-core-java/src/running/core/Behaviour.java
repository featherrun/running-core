package running.core;

public interface Behaviour {
	/**
	 * 启动
	 */
	void awake();

	/**
	 * 销毁
	 */
	void onDestroy();
}
