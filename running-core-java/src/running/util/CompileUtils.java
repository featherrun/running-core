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

import running.core.ILogger;
import running.core.Running;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;

/**
 * Compiling a java file, running time.
 */
public class CompileUtils {
	final ILogger logger = Running.getLogger(getClass());

	/**
	 * Compiling
	 *
	 * @param filename xxx.java
	 * @return result
	 */
	public boolean compile(String filename) {
		//new com.sun.tools.javac.main.Main(null).compile(new String[]{ filename });
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			logger.error("JavaCompiler is Null!!!");
			return false;
		}
		//compiler.run(null, null, null, javaName);
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		//Iterable<String> options = Arrays.asList("-d", "bin/");
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(filename);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		boolean res = task.call();
		try {
			fileManager.close();
		} catch (IOException ignored) {
		}
		return res;
	}
}
