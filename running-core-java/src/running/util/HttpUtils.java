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
import running.core.Receivable;
import running.core.Running;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpUtils {
	final Logger logger = Running.getLogger(getClass());
	final Executor executor = Executors.newSingleThreadExecutor();
	final class HttpRequest implements Runnable {
		String url;
		String method;
		String body;
		Receivable<String> receivable;
		@Override
		public void run() {
			String res = request(url);
			if (receivable != null) {
				try {
					receivable.onReceive(res);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 发起HTTP请求（异步）
	 * @param url
	 * @param receivable
	 */
	public void requestAsync(String url, Receivable<String> receivable) {
		requestAsync(url, "GET", null, receivable);
	}

	public void requestAsync(String url, String method, String body, Receivable<String> receivable) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.url = url;
		httpRequest.method = method;
		httpRequest.body = body;
		httpRequest.receivable = receivable;
		executor.execute(httpRequest);
	}

	/**
	 * 发起HTTP请求（阻塞）
	 * @param url
	 * @return
	 */
	public String request(String url) {
		return request(url, "GET", null);
	}

	public String request(String url, String method, String body) {
		return request(url, method, body, 5000, 15000);
	}

	public String request(String url, String method, String body, int connectTimeout, int readTimeout) {
		if (url == null) {
			return null;
		}
		logger.debug(url);
		StringBuilder answer = new StringBuilder();
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			//conn.setRequestProperty("Accept-Charset", "utf-8");
			//conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setUseCaches(false);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			if (method != null) {
				conn.setRequestMethod(method);
			}
			if (body != null) {
				conn.getOutputStream().write(body.getBytes());
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				if (answer.length() > 0) {
					answer.append('\n');
				}
				answer.append(line);
			}
			reader.close();
		} catch (Exception ignored) {
		}
		String res = answer.toString();
		logger.debug("HTTP RESPONSE SIZE:" + res.length() + ", CONTENT:" + (res.length() > 20 ? res.substring(0, 20)+"..." : res));
		return res;
	}
}
