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

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * Message-Digest Algorithm 5
 */
public class MD5Utils {
	final ILogger logger = Running.getLogger(getClass());

	public String md5(final String s) {
		return md5(s.getBytes(Charset.defaultCharset()));
	}

	public String md5(final byte[] bytes) {
		StringBuilder buf = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] arr = md.digest();
			for (byte b : arr) {
				int i = b;
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return buf.toString();
	}

}
