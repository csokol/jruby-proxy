package jruby;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RubyMethodsFinder {

	public ArrayList<Method> find(List<Method> allMethods) {
		ArrayList<Method> methods = new ArrayList<Method>();
		for (Method method : allMethods) {
			RubyMethod annotation = method.getAnnotation(RubyMethod.class);
			if (annotation != null) {
				methods.add(method);
			}
		}
		return methods;
	}
}
