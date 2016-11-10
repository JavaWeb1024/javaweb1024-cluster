package com.dinghao.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: BeanUtils
 * @Description: TODO
 * @mail helong
 * @date 2015-1-28 下午1:40:50
 * @version V1.0
 * 
 */

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @param exclusiveRegexp
	 *            不转换例外的属性名的正则表达匹配式
	 * @param isNullConvert
	 *            是否转换空属性
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean(Object bean,
			String exclusiveRegexp, boolean isNullConvert)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Pattern pattern = Pattern.compile(exclusiveRegexp);

		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			Matcher matcher = pattern.matcher(propertyName);
			if (!matcher.matches()) {// 不匹配
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						if (isNullConvert) {// 如果转换空属性
							returnMap.put(propertyName, "");
						}
					}
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @param exclusiveFieldtypes
	 *            不转换例外的属性的类型集合 Set<Class<?>> exclusiveFieldtypesSet
	 * @param isNullConvert
	 *            是否转换空属性
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> convertBean(Object bean,
			Set<Class<?>> exclusiveFieldtypesSet, boolean isNullConvert)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			Class<?> propertyType = descriptor.getPropertyType();

			if (!propertyName.equals("class")) {
				if (!exclusiveFieldtypesSet.contains(propertyType)) {// 如果该属性类型不包含在排除类型中则转换
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						if (isNullConvert) {
							returnMap.put(propertyName, "");
						}
					}
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = null;
				try {
					value = map.get(propertyName);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 
	 * 会将数据源bean中的所有可以拷贝到目标bean中的属性拷贝过去（即属性名和属性类型一致）</br>
	 * <p style="color:red;">
	 * 确切的说对于不是基本类型的属性这里不是拷贝，而是两个bean共用这些属性（属性会引用到同一个实例），
	 * 所以当你改变源bean中属性的值是会影响到目标bean的
	 * ，不过你可以不用担心源bean的回收，会影响到目标bean的属性，因为这些属性还有新的主人，所以如果你使用这个方法仅仅是因为想丢掉一堆的get
	 * set 逻辑，那么你可以 在执行完这个方法的时候，将源bean置空，防止对它的误操作而出现莫名的错误
	 * 
	 * @Title copyProperties
	 * @Description TODO
	 * @param dest
	 *            目标bean
	 * @param orig
	 *            数据源bean
	 * @return void
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws IntrospectionException
	 */
	public static void copyProperties(Object dest, Object orig)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		try {
			BeanInfo destbeanInfo = Introspector.getBeanInfo(dest.getClass());
			PropertyDescriptor[] destpropertyDescriptors = destbeanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < destpropertyDescriptors.length; i++) {
				PropertyDescriptor destdescriptor = destpropertyDescriptors[i];
				String destpropertyName = destdescriptor.getName();
				if (!destpropertyName.equals("class")) {
					int propertyIndex = getPropertyIndexByDesc(orig,
							destdescriptor);
					if (propertyIndex >= 0) {// 在源bean中找到了这个属性
						Method getMethod = Introspector.getBeanInfo(
								orig.getClass()).getPropertyDescriptors()[propertyIndex]
								.getReadMethod();
						if (getMethod != null) {
							Object value = getMethod
									.invoke(orig, new Object[0]);
							Object[] args = new Object[1];
							args[0] = value;
							Method setMethod = destdescriptor.getWriteMethod();
							if (setMethod != null) {
								setMethod.invoke(dest, args);
							} else {
								throw new RuntimeException(
										"can't find the property "+ destpropertyName +"'s 'setmethod'  in "
												+ dest.getClass());
							}
						} else {
							throw new RuntimeException(
									"can't find the property "+ destpropertyName +"'s 'getmethod'  in "
											+ dest.getClass());
						}
					}// 否则不处理
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 *  会将数据源bean中的所有可以拷贝到目标bean中的属性拷贝过去（即属性名和属性类型一致）</br>
	 * <p style="color:red;">
	 * 确切的说对于不是基本类型的属性这里不是拷贝，而是两个bean共用这些属性（属性会引用到同一个实例），
	 * 所以当你改变源bean中属性的值是会影响到目标bean的
	 * ，不过你可以不用担心源bean的回收，会影响到目标bean的属性，因为这些属性还有新的主人，所以如果你使用这个方法仅仅是因为想丢掉一堆的get
	 * set 逻辑，那么你可以 在执行完这个方法的时候，将源bean置空，防止对它的误操作而出现莫名的错误
	  * @Title copyProperties
	  * @Description TODO
	  * @param dest 目标bean
	  * @param orig 数据源bean
	  * @param ifcopyNullValue 是否拷贝空值(false的情况下,数据源bean中的null值属性即使满足拷贝要求也不会被拷贝到目标bean中)
	  * @throws IllegalArgumentException
	  * @throws IllegalAccessException
	  * @throws InvocationTargetException void
	 */
	public static void copyProperties(Object dest, Object orig , boolean ifcopyNullValue)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		try {
			BeanInfo destbeanInfo = Introspector.getBeanInfo(dest.getClass());
			PropertyDescriptor[] destpropertyDescriptors = destbeanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < destpropertyDescriptors.length; i++) {
				PropertyDescriptor destdescriptor = destpropertyDescriptors[i];
				String destpropertyName = destdescriptor.getName();
				if (!destpropertyName.equals("class")) {
					int propertyIndex = getPropertyIndexByDesc(orig,
							destdescriptor);
					if (propertyIndex >= 0) {// 在源bean中找到了这个属性
						Method getMethod = Introspector.getBeanInfo(
								orig.getClass()).getPropertyDescriptors()[propertyIndex]
								.getReadMethod();
						if (getMethod != null) {
							Object value = getMethod
									.invoke(orig, new Object[0]);
							Object[] args = new Object[1];
							args[0] = value;
							Method setMethod = destdescriptor.getWriteMethod();
							if (setMethod != null) {
								if (ifcopyNullValue==false) {
									if (value!=null) {
										setMethod.invoke(dest, args);
									}
								}else {
									setMethod.invoke(dest, args);
								}
							} else {
								throw new RuntimeException(
										"can't find the property "+ destpropertyName +"'s 'setmethod'  in "
												+ dest.getClass());
							}
						} else {
							throw new RuntimeException(
									"can't find the property "+ destpropertyName +"'s 'getmethod'  in "
											+ dest.getClass());
						}
					}// 否则不处理
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

	}

	private static int getPropertyIndexByDesc(Object bean,
			PropertyDescriptor descriptor) throws IntrospectionException {
		int index = -1;
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor temdescriptor = propertyDescriptors[i];
			String tempropertyName = temdescriptor.getName();
			Class<?> temppropertyType = temdescriptor.getPropertyType();
			if (!tempropertyName.equals("class")
					&& tempropertyName.equals(descriptor.getName())
					&& temppropertyType == descriptor.getPropertyType()) {
				index = i;
			}
		}
		return index;
	}

}
