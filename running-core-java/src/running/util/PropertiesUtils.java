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

package running.util;

import running.core.Logger;
import running.core.Running;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesUtils {
	final Logger logger = Running.getLogger(getClass());
	Properties defaults;

	/**
	 * Read file
	 * @param filepath
	 * @return
	 */
	public Properties load(final String filepath) {
		return load(filepath, null);
	}

	public Properties load(final String filepath, final Properties defaults) {
		Properties properties = new Properties();
		if (defaults != null) {
			properties.putAll(defaults);
		}
		Path path = Paths.get(filepath);
		if (!Files.exists(path)) {
			return properties;
		}
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path, Charset.defaultCharset());
			properties.load(reader);
			this.defaults = properties;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignored) {
				}
			}
		}
		return properties;
	}

	/**
	 * Read parameter
	 * @param args
	 * @return
	 */
	public Properties load(final String[] args) {
		return load(args, null);
	}

	public Properties load(final String[] args, final Properties defaults) {
		Properties properties = new Properties();
		if (defaults != null) {
			properties.putAll(defaults);
		}
		if (args == null || args.length == 0) {
			return properties;
		}
		String str = org.apache.commons.lang3.StringUtils.join(args, System.getProperty("line.separator"));
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(str.getBytes());
			properties.load(inputStream);
			this.defaults = properties;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ignored) {
				}
			}
		}
		return properties;
	}

	/**
	 * Read (the last load) value
	 * @param key
	 * @return
	 */
	public String getProperty(final String key) {
		return defaults != null ? defaults.getProperty(key) : null;
	}

	public String getProperty(final String key, final String defaultValue) {
		return defaults != null ? defaults.getProperty(key, defaultValue) : defaultValue;
	}
}
