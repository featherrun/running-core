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

package running.help;

import running.util.HostUtils;

import java.util.Calendar;

/**
 * reference: twitter snowflake
 */
public class IdWorker {
	protected final long workerId;
	protected final long epoch;
	protected long last;
	protected long sequence;

	public IdWorker() {
		this(0, 0);
	}

	public IdWorker(long workerId, long epoch) {
		if (workerId <= 0) {
			byte[] ip = new HostUtils().getLocalHost().getAddress();
			workerId = (ip[2] << 8) + ip[3];
		}
		if (epoch <= 0) {
			Calendar c = Calendar.getInstance();
			c.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
			epoch = c.getTimeInMillis();
		}

		this.workerId = workerId;
		this.epoch = trans(epoch);
	}

	public long getWorkerId() {
		return workerId;
	}

	public long getId() {
		return nextId();
	}

	protected long trans(long timeMillis) {
		return ((int) (timeMillis / 1000)) >>> 6; //Every 64 seconds
	}

	protected synchronized long nextId() {
		long t = trans(System.currentTimeMillis());
		if (t < last) {
			throw new IllegalArgumentException("The clock went backwards.");
		}
		if (last == t) {
			sequence++;
			if (sequence >= 0xFFFF) {
				throw new IllegalArgumentException("The sequence is full.");
			}
		} else {
			sequence = 0;
			last = t;
		}
		return ((t - epoch) << 32) | (workerId << 16) | sequence;
	}

	@Override
	public String toString() {
		return "IdWorker{" + "workerId=" + workerId +
				", epoch=" + epoch +
				", last=" + last +
				", sequence=" + sequence +
				'}';
	}


	/*
	public static void main(String[] args) {
		IdWorker idWorker = new IdWorker();
		for (int i=0; i<1000; i++) {
			String s = String.valueOf(idWorker.getId());
			System.out.println(s + ":" + s.length());
		}
	}
	*/

}
