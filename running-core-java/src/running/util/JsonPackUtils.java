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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonPackUtils {
	/**
	 * pack json
	 *
	 * {"a":"xxx","b":"xxx"} => a,b\n{$1:"xxx",$2:"xxx"}
	 *
	 * @param json
	 * @return
	 */
	public String pack(String json) {
		final Pattern pattern = Pattern.compile("\"([A-Za-z0-9_]+)\":");
		Matcher m = pattern.matcher(json);
		List<String> fields = new ArrayList<>();
		StringBuilder sb = new StringBuilder("\n");
		int x = 0;
		while (m.find()) {
			String f = m.group(1);
			int i = fields.indexOf(f);
			if (i == -1) {
				i = fields.size();
				fields.add(f);
			}
			sb.append(json.substring(x, m.start()));
			sb.append('$').append(i+1).append(':');
			x = m.end();
		}
		if (x < json.length()) {
			sb.append(json.substring(x));
		}
		return org.apache.commons.lang3.StringUtils.join(fields, ',') + sb;
	}

	public String unpack(String s) {
		final Pattern pattern = Pattern.compile("\\$(\\d+):");
		int i = s.indexOf('\n');
		String[] fields = org.apache.commons.lang3.StringUtils.split(s.substring(0, i), ',');
		String json = s.substring(i+1);
		Matcher m = pattern.matcher(json);
		StringBuilder sb = new StringBuilder("\n");
		int x = 0;
		while (m.find()) {
			int index = Integer.parseInt(m.group(1));
			sb.append(json.substring(x, m.start()));
			sb.append('"').append(fields[index-1]).append('"').append(':');
			x = m.end();
		}
		if (x < json.length()) {
			sb.append(json.substring(x));
		}
		return sb.toString();
	}
}
