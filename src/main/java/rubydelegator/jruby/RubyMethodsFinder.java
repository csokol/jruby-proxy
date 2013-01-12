package rubydelegator.jruby;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RubyMethodsFinder {

	public Set<Method> find(List<Method> allMethods) {
		Set<Method> methods = new HashSet<Method>();
		for (Method method : allMethods) {
			RubyMethod annotation = method.getAnnotation(RubyMethod.class);
			if (annotation != null) {
				methods.add(method);
			}
		}
		return methods;
	}
}
