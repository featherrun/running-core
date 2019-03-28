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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip
 */
public class GzipUtils {
	final Logger logger = Running.getLogger(getClass());

	public byte[] compress(byte[] data) {
		ByteArrayOutputStream out = null;
		GZIPOutputStream gzip = null;
		byte[] output = null;
		try {
			out = new ByteArrayOutputStream(data.length);
			gzip = new GZIPOutputStream(out);
			gzip.write(data, 0, data.length);
			gzip.finish();
			gzip.flush();
			out.flush();
			output = out.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException ignored) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignored) {
				}
			}
		}
		return output;
	}
}
