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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {
	/**
	 * javascript: JSON.stringify()
	 * @param value
	 * @return
	 */
	public String stringify(Object value) {
		return JSON.toJSONString(value,
				SerializerFeature.IgnoreNonFieldGetter,
				SerializerFeature.NotWriteDefaultValue,
				SerializerFeature.SkipTransientField,
				SerializerFeature.WriteNonStringKeyAsString);
	}

	/**
	 * javascript: JSON.parse()
	 * @param text
	 * @return
	 */
	public JSONObject parse(String text) {
		return JSON.parseObject(text);
	}

	public <T> T parse(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz, Feature.IgnoreNotMatch);
	}
}
