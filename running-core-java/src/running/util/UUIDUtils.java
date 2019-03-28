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

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * UUID
 *
 * @author featherrun
 * @version 2017/6/20
 */
public class UUIDUtils {
	final char[] characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	final int size = characters.length;

	public String get() {
		String s = UUID.randomUUID().toString();
		String[] arr = StringUtils.split(s, '-');
		StringBuilder ret = new StringBuilder();
		for (String a : arr) {
			long num = Long.parseLong(a, 16);
			while (num > 0) {
				ret.append(characters[(int) (num % size)]);
				num /= size;
			}
		}
		return ret.toString();
	}




	/*
	public static void main(String[] args) {
		java.util.Set<String> strings = new java.util.HashSet<>();
		UUIDUtils uuidUtils = new UUIDUtils();
		for (int i=0; i<100000000; i++) {
			String s = uuidUtils.get();
			if (strings.contains(s)) {
				System.out.println("ERROR");
			}
			strings.add(s);
			System.out.println(s + ":" + s.length());
		}
	}
	*/


}
