package jruby;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javassist.util.proxy.MethodHandler;
import proxies.ProxyMaker;

public class RubyDelegatorClass<T> {

	private final Class<T> clazz;
	private String rubyScript;
	private InputStream rubyScriptIs;

	public RubyDelegatorClass(Class<T> clazz) {
		this.clazz = clazz;
		getRubyScript(clazz);
	}

	private void getRubyScript(Class<T> clazz) {
		RubyClass annotation = clazz.getAnnotation(RubyClass.class);
		if (annotation == null) {
			throw new IllegalArgumentException("You should annotate your class with @" + RubyClass.class.getName() + " annotation");
		}
		rubyScript = annotation.value();
		if (!rubyScript.startsWith("/")) {
			rubyScript = "/" + rubyScript;
		}
		rubyScriptIs = getClass().getResourceAsStream(rubyScript);
		if (rubyScriptIs == null) {
			throw new IllegalArgumentException("Could not find " + rubyScript + " in the classpath");
		}
	}

	public T build() {
		ProxyMaker<T> proxyMaker = new ProxyMaker<>(clazz);
		List<Method> methods = Arrays.asList(clazz.getMethods());
		
		MethodHandler handlerGiven = new RubyDelegatorMethodHandler(methods, rubyScriptIs);
		
		T object = proxyMaker.proxyAllMethods(handlerGiven);
		return object;
	}

}
