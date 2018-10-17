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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * ZLib
 */
public class ZLibUtils {
	final ILogger logger = Running.getLogger(getClass());

	public byte[] compress(final byte[] data) {
		byte[] output = null;
		Deflater deflater = new Deflater();
		deflater.reset();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!deflater.finished()) {
				int i = deflater.deflate(buf);
				bos.write(buf, 0, i);
			}
			output = bos.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				bos.close();
			} catch (IOException ignored) {
			}
		}
		deflater.end();
		return output;
	}

	public byte[] decompress(final byte[] data) {
		byte[] output = null;
		Inflater inflater = new Inflater();
		inflater.reset();
		inflater.setInput(data);
		ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
		try {
			byte[] buf = new byte[1024];
			while (!inflater.finished()) {
				int i = inflater.inflate(buf);
				o.write(buf, 0, i);
			}
			output = o.toByteArray();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				o.close();
			} catch (IOException ignored) {
			}
		}
		inflater.end();
		return output;
	}

}
