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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayUtils {
	public boolean contains(Object[] arr, Object o) {
		return indexOf(arr, o) >= 0;
	}

	public int indexOf(Object[] arr, Object o) {
		int size = arr.length;
		if (o == null) {
			for (int i = 0; i < size; i++)
				if (arr[i]==null)
					return i;
		} else {
			for (int i = 0; i < size; i++)
				if (o.equals(arr[i]))
					return i;
		}
		return -1;
	}


	/**
	 * "string|..." => Array
	 */
	public String[] getStringArray(String s) {
		return getStringArray(s, '|');
	}

	public String[] getStringArray(String s, char split) {
		return org.apache.commons.lang3.StringUtils.split(s, split);
	}

	public String[] getStringArray(String s, String split) {
		return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(s, split);
	}

	/**
	 * "string,string|..." => Array
	 */
	public String[][] getStringArray2(String s) {
		return getStringArray2(s, '|', ',');
	}

	public String[][] getStringArray2(String s, char split, char split2) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		String[] arr2 = getStringArray(arr[0], split2);
		int len1 = arr.length;
		int len2 = arr2.length;
		String[][] result = new String[len1][len2];
		for (int i = 0; i < len1; i++) {
			arr2 = getStringArray(arr[i], split2);
			System.arraycopy(arr2, 0, result[i], 0, len2);
		}
		return result;
	}

	/**
	 * "int|..." => Array
	 */
	public int[] getIntegerArray(String s) {
		return getIntegerArray(s, '|');
	}

	public int[] getIntegerArray(String s, char split) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		int len = arr.length;
		int[] result = new int[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.parseInt(arr[i]);
		}
		return result;
	}

	/**
	 * "int,int|..." => Array
	 */
	public int[][] getIntegerArray2(String s) {
		return getIntegerArray2(s, '|', ',');
	}

	public int[][] getIntegerArray2(String s, char split, char split2) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		String[] arr2;
		int len1 = arr.length;
		int len2 = 0;
		for (int i = 0; i < len1; i++) {
			len2 = Math.max(len2, getStringArray(arr[i], split2).length);
		}
		int[][] result = new int[len1][len2];
		for (int i = 0; i < len1; i++) {
			arr2 = getStringArray(arr[i], split2);
			for (int j = 0, n = arr2.length; j < n; j++) {
				result[i][j] = Integer.parseInt(arr2[j]);
			}
		}
		return result;
	}

	/**
	 * "double|..." => Array
	 *
	 * @param s
	 * @return
	 */
	public double[] getDoubleArray(String s) {
		return getDoubleArray(s, '|');
	}

	public double[] getDoubleArray(String s, char split) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		int len = arr.length;
		double[] result = new double[len];
		for (int i = 0; i < len; i++) {
			result[i] = Double.parseDouble(arr[i]);
		}
		return result;
	}

	/**
	 * "double,double|..." => Array
	 */
	public double[][] getDoubleArray2(String s) {
		return getDoubleArray2(s, '|', ',');
	}

	public double[][] getDoubleArray2(String s, char split, char split2) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		String[] arr2 = getStringArray(arr[0], split2);
		int len1 = arr.length;
		int len2 = arr2.length;
		double[][] result = new double[len1][len2];
		for (int i = 0; i < len1; i++) {
			arr2 = getStringArray(arr[i], split2);
			for (int j = 0; j < len2; j++) {
				result[i][j] = Double.parseDouble(arr2[j]);
			}
		}
		return result;
	}



	/**
	 * "string|..." => List
	 */
	public List<String> getStringList(String s) {
		return getStringList(s, '|');
	}

	public List<String> getStringList(String s, char split) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, arr);
		return list;
	}

	/**
	 * "int|..." => List
	 */
	public List<Integer> getIntegerList(String s) {
		return getIntegerList(s, '|');
	}

	public List<Integer> getIntegerList(String s, char split) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		List<Integer> list = new ArrayList<Integer>();
		for (String str : arr) {
			list.add(Integer.valueOf(str));
		}
		return list;
	}

	/**
	 * "double|..." => List
	 */
	public List<Double> getDoubleList(String s) {
		return getDoubleList(s, '|');
	}

	public List<Double> getDoubleList(String s, char split) {
		if (s == null) {
			return null;
		}
		String[] arr = getStringArray(s, split);
		List<Double> list = new ArrayList<Double>();
		for (String str : arr) {
			list.add(Double.valueOf(str));
		}
		return list;
	}


}
