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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
	final Logger logger = Running.getLogger(getClass());

	/**
	 * load all *.class file
	 */
	public Set<Class<?>> getClasses(String packageName) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					// 以文件的形式保存在服务器上
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findAndAddClassesInPackageByFile(packageName, filePath, classes);
				} else if ("jar".equals(protocol)) {
					// jar包文件
					JarFile jar;
					try {
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						Enumeration<JarEntry> entries = jar.entries();
						while (entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							if (entry.isDirectory()) {
								continue;
							}
							String name = entry.getName();
							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								if (idx != -1) {
									if (name.endsWith(".class")) {
										packageName = name.substring(0, idx).replace('/', '.');
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										classes.add(getClass(packageName, className));
									}
								}
							}
						}
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return classes;
	}

	private void findAndAddClassesInPackageByFile(String packageName, String packagePath, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || (file.getName().endsWith(".class"));
			}
		});
		if (dirFiles != null) {
			for (File file : dirFiles) {
				if (file.isDirectory()) {
					findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), classes);
				} else {
					String className = file.getName().substring(0, file.getName().length() - 6);
					classes.add(getClass(packageName, className));
				}
			}
		}
	}

	/**
	 * load a class
	 */
	public Class<?> getClass(String packageName, String className) {
		Class<?> cls = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			cls = classLoader.loadClass(packageName + '.' + className);
			//cls = Class.forName(packageName + '.' + className);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return cls;
	}
}
