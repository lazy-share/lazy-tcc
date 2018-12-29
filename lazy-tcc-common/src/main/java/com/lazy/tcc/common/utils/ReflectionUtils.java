package com.lazy.tcc.common.utils;


import com.lazy.tcc.common.enums.JavaType;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author laizhiyuan
 * @since 2017/12/29.
 * <p>反射工具类</p>
 */
@SuppressWarnings("all")
public abstract class ReflectionUtils {

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Object handler = Proxy.getInvocationHandler(annotation);

        Field f;

        f = handler.getClass().getDeclaredField("memberValues");

        f.setAccessible(true);

        Map<String, Object> memberValues;

        memberValues = (Map<String, Object>) f.get(handler);

        Object oldValue = memberValues.get(key);

        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {

            throw new IllegalArgumentException();
        }

        memberValues.put(key, newValue);

        return oldValue;
    }

    public static Class getDeclaringType(Class aClass, String methodName, Class<?>[] parameterTypes) {

        Method method = null;


        Class findClass = aClass;

        do {
            Class[] clazzes = findClass.getInterfaces();

            for (Class clazz : clazzes) {

                try {
                    method = clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (NoSuchMethodException e) {
                    method = null;
                }

                if (method != null) {
                    return clazz;
                }
            }

            findClass = findClass.getSuperclass();

        } while (!findClass.equals(Object.class));

        return aClass;
    }

    public static Object getNullValue(Class type) {

        if (boolean.class.equals(type)) {
            return false;
        } else if (byte.class.equals(type)) {
            return 0;
        } else if (short.class.equals(type)) {
            return 0;
        } else if (int.class.equals(type)) {
            return 0;
        } else if (long.class.equals(type)) {
            return 0;
        } else if (float.class.equals(type)) {
            return 0;
        } else if (double.class.equals(type)) {
            return 0;
        }

        return null;
    }

    /**
     * 3级父类查找
     *
     * @param fieldName
     * @param clazz
     * @return
     */
    public static Field getFieldByName(String fieldName, Class clazz) {
        if (clazz == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            try {
                field = clazz.getSuperclass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e1) {
                try {
                    field = clazz.getSuperclass().getSuperclass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return field;
    }

    /**
     * get method
     *
     * @param field
     * @param clazz
     * @return
     */
    public static Method getGetMethodByField(Field field, Class clazz) {
        if (clazz == null || field == null) {
            return null;
        }
        String fieldName = field.getName();
        Method method = getGetMethodByFieldName(fieldName, clazz);
        return method;
    }

    /**
     * 根据属性名称获取值
     *
     * @param fieldName 属性名称
     * @param clazz     Cla's's对象
     * @return 值
     */
    public static Object getValueByFieldName(String fieldName, Class clazz, Object object) {
        if (StringUtils.isBlank(fieldName) || clazz == null) {
            return null;
        }
        Method method = getGetMethodByFieldName(fieldName, clazz);
        Object value = null;
        try {
            value = method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    /**
     * 反射获取getger
     * 支持三级继承
     *
     * @param fieldName
     * @param clazz
     * @return
     */
    public static Method getGetMethodByFieldName(String fieldName, Class clazz) {
        if (clazz == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = clazz.getMethod(methodName, new Class[]{});
            return method;
        } catch (NoSuchMethodException e) {
            try {
                Method method = clazz.getSuperclass().getMethod(methodName, new Class[]{});
                return method;
            } catch (NoSuchMethodException ex) {
                try {
                    Method method = clazz.getSuperclass().getSuperclass().getMethod(methodName, new Class[]{});
                    return method;
                } catch (NoSuchMethodException exx) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * set method
     *
     * @param field
     * @param clazz
     * @return
     */
    public static Method getSetMethodByField(Field field, Class clazz) {
        if (clazz == null || field == null) {
            return null;
        }
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = clazz.getMethod(methodName, field.getType());
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得所有属性，不包括父类
     *
     * @param clazz
     * @return
     */
    public static List<String> fields(Class clazz) {
        if (clazz == null) {
            return null;
        }
        List<String> fields = new ArrayList<String>();
        Field[] listField = clazz.getDeclaredFields();
        if (listField != null) {
            for (int i = 0; i < listField.length; i++) {
                fields.add(listField[i].getName());
            }
        }
        return fields;
    }

    /**
     * 获得所有属性，包括所有父类的
     *
     * @param clazz
     * @return
     */
    public static List<String> allFields(Class clazz) {
        if (clazz == null) {
            return null;
        }
        List<String> fields = new ArrayList<String>();
        while (clazz != null) {
            fields.addAll(fields(clazz));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * set vlaue to field
     *
     * @param fieldName
     * @param value
     * @param obj
     */
    public static void setFieldValue(String fieldName, Object value, Object obj) {
        String typeName = null;
        try {
            if (StringUtils.isBlank(fieldName) || obj == null || value == null) {
                return;
            }
            Class clazz = obj.getClass();
            Field field = getFieldByName(fieldName, clazz);
            Method method = getSetMethodByField(field, clazz);
            typeName = field.getType().getSimpleName();
            if (JavaType.String.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, value.toString());
            } else if (JavaType.Boolean.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Boolean.valueOf(value.toString()));
            } else if (JavaType.Integer.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Integer.valueOf(value.toString()));
            } else if (JavaType.Date.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Date.valueOf(value.toString()));
            } else if (JavaType.Timestamp.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Timestamp.valueOf(value.toString()));
            } else if (JavaType.Time.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Time.valueOf(value.toString()));
            } else if (JavaType.Double.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Double.valueOf(value.toString()));
            } else if (JavaType.Float.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Float.valueOf(value.toString()));
            } else if (JavaType.Long.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Long.valueOf(value.toString()));
            } else if (JavaType.Short.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Short.valueOf(value.toString()));
            } else if (JavaType.Byte.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Byte.valueOf(value.toString()));
            } else if (JavaType.Character.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, Character.valueOf((Character) value));
            } else if (JavaType.BigDecimal.getValue().equalsIgnoreCase(typeName)) {
                method.invoke(obj, BigDecimal.valueOf(Long.valueOf(value.toString())));
            } else {
                method.invoke(obj, value);
            }
        } catch (Exception e) {
            System.out.println("====================> 不支持类型:" + typeName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取父类第index个泛型class
     *
     * @param clazz Class对象
     * @param index 第几个泛型参数
     * @return 泛型Class对象
     */
    public static Class getSuperGenericityClass(Class clazz, int index) {
        if (clazz == null) {
            return null;
        }
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types == null || types.length < index) {
                throw new RuntimeException("genericity number < index param: " + index);
            }
            try {
                Class c = (Class) types[index - 1];
                if (c == null) {
                    throw new RuntimeException("not found genericity param in super");
                }
                return c;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("not found genericity param in super");
    }

    /**
     * 获取父类第index个泛型实例
     *
     * @param clazz Class对象
     * @param index 第几个泛型参数
     * @return 父类泛型实例对象
     */
    public static Object getSuperGenericityInstance(Class clazz, int index) {
        try {
            return getSuperGenericityClass(clazz, index).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
