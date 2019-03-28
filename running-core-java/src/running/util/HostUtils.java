/*
 * Copyright 2013-2019 featherrun
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

import running.core.Running;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class HostUtils {

	public String getLocalIp() {
		InetAddress address = getLocalHost();
		return address != null ? address.getHostAddress() : null;
	}

	public InetAddress getLocalHost() {
		InetAddress find = null;
		try {
			Enumeration<NetworkInterface> nie = NetworkInterface.getNetworkInterfaces();
			while (nie.hasMoreElements()) {
				NetworkInterface ni = nie.nextElement();
				Enumeration<InetAddress> ie = ni.getInetAddresses();
				while (ie.hasMoreElements()) {
					InetAddress i = ie.nextElement();
					if (!i.isLoopbackAddress()) {
						if (i.isSiteLocalAddress()) {
							return i;
						} else if (find == null) {
							find = i;
						}
					}
				}
			}
			if (find == null) {
				find = InetAddress.getLocalHost();
			}
		} catch (Exception e) {
			Running.getLogger(getClass()).error(e.getMessage(), e);
		}
		return find;
	}

}
