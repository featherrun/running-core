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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
	final Logger logger = Running.getLogger(getClass());

	/**
	 * Read a resource file
	 *
	 * @param path
	 * @return
	 */
	public String getResource(String path) {
		InputStream is = ClassLoader.getSystemResourceAsStream(path);
		String lineSeparator = System.getProperty("line.separator");
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			if (is != null) {
				br = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()));
				while (br.ready()) {
					sb.append(br.readLine()).append(lineSeparator);
				}
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
		}
		return sb.toString();
	}

	/**
	 * Read a text file
	 *
	 * @param path
	 * @return
	 */
	public String read(String path) {
		return read(Paths.get(path));
	}

	public String read(Path path) {
		if (!Files.exists(path)) {
			return null;
		}
		try {
			return new String(Files.readAllBytes(path), Charset.defaultCharset());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Save a text file
	 *
	 * @param path
	 * @param content
	 */
	public void save(String path, String content) {
		save(path, content.getBytes(Charset.defaultCharset()));
	}

	public void save(String path, byte[] bytes) {
		try {
			Files.write(Paths.get(path), bytes);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Automatically create folders
	 *
	 * @param path
	 */
	public void mkdirs(String path) {
		Path p = Paths.get(path);
		if (Files.exists(p)) {
			return;
		}
		try {
			Files.createDirectories(p);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * Delete files or folders
	 *
	 * @param path
	 */
	public void delete(String path) {
		if (Files.exists(Paths.get(path))) {
			delete(new File(path));
		}
	}

	public void delete(File file) {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					delete(child);
				}
			}
		}
		file.delete();
	}

	/**
	 * Download the remote file and save it to the local
	 *
	 * @param remoteFile
	 * @param localFile
	 */
	public boolean download(String remoteFile, String localFile) {
		boolean res = true;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File f = new File(localFile);
			URL url = new URL(remoteFile);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
		} catch (Exception e) {
			res = false;
			logger.error(e.getMessage(), e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException ignored) {
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException ignored) {
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}
		return res;
	}
}
