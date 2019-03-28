/*
 * Copyright 2013-2018 featherrun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package running.core;

import java.util.HashMap;
import java.util.Map;

public final class Running {
	private static final Map<Class<?>, Object> objectMap = new HashMap<>();
	private static LoggerFactory loggerFactory = null;
	private static boolean fixed;

	@SuppressWarnings("unchecked")
	public static <T> void set(T obj) {
		set((Class<T>) obj.getClass(), obj);
	}

	public static <T> void set(Class<T> cls, T obj) {
		if (fixed) {
			if (!objectMap.containsKey(cls)) {
				Logger logger = getLogger(Running.class);
				if (logger != null) {
					logger.error("Running fixed."+cls.toString());
				}
				return;
			}
		}
		objectMap.put(cls, obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> cls) {
		return (T) objectMap.get(cls);
	}

	/**
	 * Logger
	 */
	public static Logger getLogger(Class<?> cls) {
		return getLoggerFactory().getLogger(cls);
	}

	public static LoggerFactory getLoggerFactory() {
		return loggerFactory != null ? loggerFactory : get(LoggerFactory.class);
	}

	public static void setLoggerFactory(LoggerFactory loggerFactory) {
		Running.loggerFactory = loggerFactory;
	}

	/**
	 * No new values can be set
	 */
	public static void fix() {
		fixed = true;
	}
}
