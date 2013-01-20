package rubydelegator.jruby;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javassist.util.proxy.MethodHandler;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

public class RubyMethodHandler<T> implements MethodHandler {

	private Set<Method> methodsToProxy;
	private final Ruby runtime;
	private IRubyObject rubyClass;
	private IRubyObject rubyInstance;
	private final TypeConverter converter;

	public RubyMethodHandler(List<Method> allMethods,
			Ruby runtime, Class<T> javaClass, TypeConverter converter) {
		this.runtime = runtime;
		this.converter = converter;
		this.methodsToProxy = new RubyMethodsFinder().find(allMethods);
		rubyClass = runtime.evalScriptlet(javaClass.getSimpleName());
		rubyInstance = rubyClass.callMethod(runtime.getCurrentContext(), "new");
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		IRubyObject[] rubyParams = new IRubyObject[args.length];
		if (methodsToProxy.contains(thisMethod)) {
			for (int i = 0; i < args.length; i++) {
				IRubyObject rubyObject = converter.convert(args[i]);
				rubyParams[i] = rubyObject;
			}
			IRubyObject result = rubyInstance.callMethod(runtime.getCurrentContext(), thisMethod.getName(), rubyParams);
			return result.toJava(thisMethod.getReturnType());
		}
		return proceed.invoke(self, args);
	}


}
