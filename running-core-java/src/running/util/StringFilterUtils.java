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

import java.util.regex.Pattern;

public class StringFilterUtils {
	final char[] filters = new char[]{'|', '#', '<', '>', '%', '*', '/', '\\', '='};
	final char[] blanks = new char[]{'\n', '\r', '\t', '\f', ' ', '　'};
	final Pattern numberPattern = Pattern.compile("^\\-?\\d+\\.?\\d*$");

	public boolean isNumeric(String s) {
		return numberPattern.matcher(s).matches();
	}

	public boolean hasLimit(String s) {
		for (char c : filters) {
			if (s.indexOf(c) >= 0) {
				return true;
			}
		}
		return false;
	}

	public boolean hasEmoji(String s) {
		return (s.contains("\uD83C") || s.contains("\uD83D"));
	}

	public boolean hasBlank(String s) {
		for (char c : blanks) {
			if (s.indexOf(c) >= 0) {
				return true;
			}
		}
		return false;
	}

}
