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

import running.core.Behaviour;
import running.core.Logger;
import running.core.Running;

public class BehaviourUtils {
	final Logger logger = Running.getLogger(getClass());

	public void awake(final Iterable<?> iterable) {
		for (Object data : iterable) {
			if (data instanceof Behaviour) {
				try {
					((Behaviour) data).awake();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public void onDestroy(final Iterable<?> iterable) {
		for (Object data : iterable) {
			if (data instanceof Behaviour) {
				try {
					((Behaviour) data).onDestroy();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

}
