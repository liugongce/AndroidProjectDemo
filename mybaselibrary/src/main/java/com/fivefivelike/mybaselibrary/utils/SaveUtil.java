package com.fivefivelike.mybaselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SaveUtil {
	private static SaveUtil save;
	private SharedPreferences sp;

	private SaveUtil() {
	}

	/**
	 * 得到单一实例
	 *
	 * @return
	 */
	public static SaveUtil getInstance() {
		if (save == null) {
			synchronized (SaveUtil.class) {
				if (save == null) {
					save = new SaveUtil();
				}
			}
		}
		return save;
	}

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 *
	 * @param key
	 * @param object
	 */
	public void put(String key, Object object) {
		initSpIfNull();
		SharedPreferences.Editor editor = sp.edit();
		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}
		editor.commit();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 *
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public Object get(String key, Object defaultObject) {
		initSpIfNull();
		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}
		return null;
	}


	public String getString(String key, String def) {
		return (String) get(key, def);
	}

	public int getInt(String key, int def) {
		return (int) get(key, def);
	}

	public boolean getBoolean(String key, boolean def) {
		return (boolean) get(key, def);
	}

	public float getBoolean(String key, float def) {
		return (float) get(key, def);
	}

	public long getBoolean(String key, long def) {
		return (long) get(key, def);
	}

	/**
	 * 如果为空那么初始化
	 */
	private void initSpIfNull() {
		if (sp == null) {
			sp = GlobleContext.getInstance().getApplicationContext().getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		}
	}

	/**
	 * 根据键移除
	 *
	 * @param key
	 */
	public void remove(String key) {
		initSpIfNull();
		sp.edit().remove(key).commit();
	}

	/**
	 * 清空文件
	 */
	public void clear() {
		initSpIfNull();
		sp.edit().clear();
	}

	/**
	 * 返回所有的键值对
	 *
	 * @return
	 */
	public Map<String, ?> getAll() {
		initSpIfNull();
		return sp.getAll();
	}

	/**
	 * 获取包名
	 *
	 * @return
	 */
	private String getPackageName() {
		if (GlobleContext.getInstance().getApplicationContext() != null) {
			return GlobleContext.getInstance().getApplicationContext().getPackageName();
		}
		return null;
	}
	//    /**
	//     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	//     *
	//     * @author zhy
	//     */
	//    private static class SharedPreferencesCompat {
	//        private static final Method sApplyMethod = findApplyMethod();
	//        /**
	//         * 反射查找apply的方法
	//         *
	//         * @return
	//         */
	//        @SuppressWarnings({"unchecked", "rawtypes"})
	//        private static Method findApplyMethod() {
	//            try {
	//                Class clz = SharedPreferences.Editor.class;
	//                return clz.getMethod("apply");
	//            } catch (NoSuchMethodException e) {
	//            }
	//
	//            return null;
	//        }
	//
	//        /**
	//         * 如果找到则使用apply执行，否则使用commit
	//         *
	//         * @param editor
	//         */
	//        public static void apply(SharedPreferences.Editor editor) {
	//            try {
	//                if (sApplyMethod != null) {
	//                    sApplyMethod.invoke(editor);
	//                    return;
	//                }
	//            } catch (IllegalArgumentException e) {
	//            } catch (IllegalAccessException e) {
	//            } catch (InvocationTargetException e) {
	//            }
	//            editor.commit();
	//        }
	//    }
}
