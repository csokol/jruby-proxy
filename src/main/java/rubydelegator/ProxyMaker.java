package rubydelegator;

import java.lang.reflect.Method;
import java.util.Set;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;


public class ProxyMaker<T> {

	private final Class<T> classToProxy;
	private MethodFilter filter;

	public ProxyMaker(Class<T> classToProxy) {
		this.classToProxy = classToProxy;
	}

	private class Filter implements MethodFilter {
		private final Set<Method> methodsToProxy;

		public Filter(Set<Method> methodsToProxy) {
			this.methodsToProxy = methodsToProxy;
		}

		@Override
		public boolean isHandled(Method m) {
			return methodsToProxy.contains(m);
		}
	}

	@SuppressWarnings("unchecked")
	public T proxy(MethodHandler handlerGiven, Set<Method> methodsToProxy) {
		this.filter = new Filter(methodsToProxy);
		try {
			ProxyFactory proxyFactory = new ProxyFactory();
			proxyFactory.setSuperclass(classToProxy);
			proxyFactory.setFilter(filter);
			Class<T> proxiedClass = proxyFactory.createClass();
			Object proxy = proxiedClass.newInstance();
			((ProxyObject) proxy).setHandler(handlerGiven);
			return (T) proxy;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	};

}
