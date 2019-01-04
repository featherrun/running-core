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

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip file util
 */
public class ZipUtils {
	final ILogger logger = Running.getLogger(getClass());

	/**
	 * files/folders to xxx.zip
	 *
	 * @param file       Source files/folders
	 * @param output     Zip filename
	 * @param includeDir Does it contain an outer folder
	 * @return 是否成功
	 */
	public boolean compress(File file, String output, boolean includeDir) {
		boolean res = false;
		ZipOutputStream out = null;
		try {
			Files.deleteIfExists(Paths.get(output));
			out = new ZipOutputStream(new FileOutputStream(output));
			compress(file, out, includeDir ? file.getName() : "");
			res = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignored) {
				}
			}
		}
		return res;
	}

	void compress(File sourceFile, ZipOutputStream zos, String name) throws Exception {
		byte[] buf = new byte[2 * 1024];
		if (sourceFile.isFile()) {
			zos.putNextEntry(new ZipEntry(name));
			FileInputStream in = new FileInputStream(sourceFile);
			int len;
			while ((len = in.read(buf)) != -1) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if (listFiles == null || listFiles.length == 0) {
				zos.putNextEntry(new ZipEntry(name));
				zos.closeEntry();
			} else {
				for (File file : listFiles) {
					compress(file, zos, file.getName());
				}
			}
		}
	}


	/**
	 * Read all text files
	 *
	 * @param file Zip filename
	 * @return
	 */
	public Map<String, String> read(String file) {
		Map<String, String> textMap = new HashMap<>();
		ZipFile zf = null;
		ZipInputStream zis = null;
		try {
			zf = new ZipFile(file);
			zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
			ZipEntry ze;
			while ((ze = zis.getNextEntry()) != null) {
				if (ze.isDirectory()) {
					continue;
				}
				String text = read(zf.getInputStream(ze));
				textMap.put(ze.getName(), text);
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (zis != null) {
				try {
					zis.close();
				} catch (IOException ignored) {
				}
			}
			if (zf != null) {
				try {
					zf.close();
				} catch (IOException ignored) {
				}
			}
		}
		return textMap;
	}

	String read(InputStream in) {
		final String separator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append(separator);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ignored) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignored) {
				}
			}
		}
		return sb.toString();
	}
}
