package proxies;

import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;


public class ProxyMaker<T> {

	private final Class<T> classToProxy;

	public ProxyMaker(Class<T> classToProxy) {
		this.classToProxy = classToProxy;
	}

	@SuppressWarnings("unchecked")
	public T proxyAllMethods(MethodHandler methodHandler) {
		try {
			ProxyFactory proxyFactory = new ProxyFactory();
			proxyFactory.setSuperclass(classToProxy);
			proxyFactory.setFilter(new MethodFilter() {
				@Override
				public boolean isHandled(Method m) {
					return true;
				}
			});
			Class<T> proxiedClass = proxyFactory.createClass();
			Object proxy = proxiedClass.newInstance();
			((ProxyObject) proxy).setHandler(methodHandler);
			
			return (T) proxy;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
