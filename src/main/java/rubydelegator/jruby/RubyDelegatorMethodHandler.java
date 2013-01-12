package rubydelegator.jruby;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javassist.util.proxy.MethodHandler;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

public class RubyDelegatorMethodHandler<T> implements MethodHandler {

	private Set<Method> methodsToProxy;
	private final Ruby runtime;
	private IRubyObject rubyClass;
	private IRubyObject rubyInstance;

	public RubyDelegatorMethodHandler(List<Method> allMethods,
			Ruby runtime, Class<T> javaClass) {
		this.runtime = runtime;
		this.methodsToProxy = new RubyMethodsFinder().find(allMethods);
		rubyClass = runtime.evalScriptlet(javaClass.getSimpleName());
		rubyInstance = rubyClass.callMethod(runtime.getCurrentContext(), "new");
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		if (methodsToProxy.contains(thisMethod)) {
			IRubyObject result = rubyInstance.callMethod(runtime.getCurrentContext(), thisMethod.getName());
			return result.toJava(thisMethod.getReturnType());
		}
		return proceed.invoke(self, args);
	}


}
