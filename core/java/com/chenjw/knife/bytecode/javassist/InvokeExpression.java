package com.chenjw.knife.bytecode.javassist;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 调用表达式
 * 
 * @author chenjw 2012-6-14 上午12:18:44
 */
public class InvokeExpression extends Expression {

	/**
	 * 生成方法调用的表达式
	 * 
	 * @param method
	 *            调用的方法
	 * @param field
	 * @param param
	 */
	public InvokeExpression(Method method, Expression field, Expression[] param) {
		StringBuffer sb = new StringBuffer();
		sb.append(getFieldString(method, field) + "." + method.getName() + "(");
		for (int i = 0; i < method.getParameterTypes().length; i++) {
			if (i > 0) {
				sb.append(",");
			}
			sb.append(param[i].box(method.getParameterTypes()[i])
					.cast(method.getParameterTypes()[i]).getCode());
		}
		sb.append(")");
		this.code = sb.toString();
		this.type = method.getReturnType();
	}

	private static String getFieldString(Method method, Expression field) {
		if (Modifier.isStatic(method.getModifiers())) {
			return method.getDeclaringClass().getName();
		} else {
			return "(" + field.getCode() + ")";
		}
	}
}
