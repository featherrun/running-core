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

public class StringUtils {

	/**
	 * Whether or not a blank (meaningless) character
	 * @param cs
	 * @return
	 */
	public boolean isBlank(final CharSequence cs) {
		return isEmpty(cs) || cs.equals("0") || cs.equals(" ") || cs.equals("null");
	}

	public boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * Is it empty
	 * @param cs
	 * @return
	 */
	public boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}

	/**
	 * Title-case
	 * @return
	 */
	public String firstUpperCase(final String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * Name normalization
	 * @param name Source field
	 * @param first title-case
	 * @return
	 */
	public String normalize(final String name, boolean first) {
		if (name == null) return null;
		StringBuilder sb = new StringBuilder();
		String[] arr = split(name, '_');
		for (String s : arr) {
			if (first) {
				sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
			} else {
				sb.append(s);
				first = true;
			}
		}
		return sb.toString();
	}

	/**
	 * Merge string
	 * @param iterable
	 * @return
	 */
	public String join(final Iterable<?> iterable) {
		return join(iterable, '|');
	}

	public String join(final Iterable<?> iterable, final char separator) {
		return org.apache.commons.lang3.StringUtils.join(iterable, separator);
	}

	public String join(final Iterable<?> iterable, final String separator) {
		return org.apache.commons.lang3.StringUtils.join(iterable, separator);
	}

	public String join(final Object[] array) {
		return join(array, '|');
	}

	public String join(final Object[] array, final char separator) {
		return org.apache.commons.lang3.StringUtils.join(array, separator);
	}

	public String join(final Object[] array, final String separator) {
		return org.apache.commons.lang3.StringUtils.join(array, separator);
	}

	public String join(final int[] array) {
		return join(array, '|');
	}

	public String join(final int[] array, final char separator) {
		return org.apache.commons.lang3.StringUtils.join(array, separator);
	}

	public String join(final int[] array, final String separator) {
		return org.apache.commons.lang3.StringUtils.join(array, separator);
	}

	/**
	 * Separate the string
	 * @param s
	 * @return
	 */
	public String[] split(final String s) {
		return split(s, '|');
	}

	public String[] split(final String s, final char separator) {
		return org.apache.commons.lang3.StringUtils.split(s, separator);
	}

	public String[] split(final String s, final String separator) {
		return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(s, separator);
	}
}
