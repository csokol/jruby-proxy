package rubydelegator;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;


public class ProxyMaker<T> {

	private final Class<T> classToProxy;
	private MethodFilter allMethods;

	public ProxyMaker(Class<T> classToProxy) {
		this.classToProxy = classToProxy;
		this.allMethods = new AllMethods(); 
	}

	@SuppressWarnings("unchecked")
	public T proxyAllMethods(MethodHandler handlerGiven) {
		try {
			ProxyFactory proxyFactory = new ProxyFactory();
			proxyFactory.setSuperclass(classToProxy);
			proxyFactory.setFilter(allMethods);
			Class<T> proxiedClass = proxyFactory.createClass();
			Object proxy = proxiedClass.newInstance();
			((ProxyObject) proxy).setHandler(handlerGiven);
			
			return (T) proxy;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private class AllMethods implements MethodFilter {
		@Override
		public boolean isHandled(Method m) {
			return true;
		}
	};

}
