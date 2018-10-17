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

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PrintLogger implements ILogger {
	protected PrintStream stream;
	protected List<String> limitLevels;

	public PrintLogger() {
		this(System.out);
	}

	public PrintLogger(PrintStream stream, String ...limitLevels) {
		this.stream = stream;
		this.limitLevels = (limitLevels != null && limitLevels.length > 0) ? Arrays.asList(limitLevels) : null;
	}

	protected void print(String level, Object message, Throwable t) {
		if (limitLevels != null && !limitLevels.contains(level)) {
			return;
		}
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date());
		StackTraceElement[] elements = new Throwable().getStackTrace();
		StackTraceElement stack = elements[2];
		String msg = message != null ? message.toString() : "null";
		String s = String.format("%s %-5s [%s:%d] %s", time, level, stack.getClassName(), stack.getLineNumber(), msg);
		stream.println(s);
		if (t != null) {
			t.printStackTrace(stream);
		}
	}

	@Override
	public ILogger getLogger(Class<?> cls) {
		return this;
	}

	@Override
	public void fatal(Object message) {
		print("FATAL", message, null);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		print("FATAL", message, t);
	}

	@Override
	public void error(Object message) {
		print("ERROR", message, null);
	}

	@Override
	public void error(Object message, Throwable t) {
		print("ERROR", message, t);
	}

	@Override
	public void warn(Object message) {
		print("WARN", message, null);
	}

	@Override
	public void warn(Object message, Throwable t) {
		print("WARN", message, t);
	}

	@Override
	public void info(Object message) {
		print("INFO", message, null);
	}

	@Override
	public void info(Object message, Throwable t) {
		print("INFO", message, t);
	}

	@Override
	public void debug(Object message) {
		print("DEBUG", message, null);
	}

	@Override
	public void debug(Object message, Throwable t) {
		print("DEBUG", message, t);
	}

	@Override
	public void trace(Object message) {
		print("TRACE", message, null);
	}

	@Override
	public void trace(Object message, Throwable t) {
		print("TRACE", message, t);
	}
}
