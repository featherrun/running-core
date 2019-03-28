/*
 * Copyright 2013-2019 featherrun
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

package running.help;

public class ClassBuilder {
	protected StringBuilder sb;
	protected int indent;
	protected boolean isNewLine;
	protected String lineSeparator;

	public ClassBuilder() {
		this(0);
	}

	public ClassBuilder(int indent) {
		this.indent = indent;
		sb = new StringBuilder();
		isNewLine = true;
		lineSeparator = System.getProperty("line.separator");
	}

	public ClassBuilder start() {
		indent++;
		return this;
	}

	public ClassBuilder startWith(String value) {
		appendLine(value);
		return start();
	}

	public ClassBuilder end() {
		indent--;
		return this;
	}

	public ClassBuilder endWith(String value) {
		end();
		return appendLine(value);
	}

	public ClassBuilder append(Object value) {
		if (isNewLine) {
			isNewLine = false;
			for (int i = 0; i < indent; i++) {
				sb.append('\t');
			}
		}
		sb.append(value);
		return this;
	}

	public ClassBuilder appendLine() {
		sb.append(lineSeparator);
		isNewLine = true;
		return this;
	}

	public ClassBuilder appendLine(Object value) {
		append(value);
		return appendLine();
	}

	public ClassBuilder deleteLine() {
		if (isNewLine && sb.length() > 0) {
			sb.delete(sb.length()-lineSeparator.length(), sb.length());
			isNewLine = false;
		}
		return this;
	}

	@Override
	public String toString() {
		return sb.toString();
	}

}
