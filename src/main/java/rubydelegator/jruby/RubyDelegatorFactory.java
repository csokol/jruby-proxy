package rubydelegator.jruby;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javassist.util.proxy.MethodHandler;

import org.jruby.Ruby;

import rubydelegator.ProxyMaker;
import rubydelegator.RubyClass;

public class RubyDelegatorFactory<T> {

	private final Class<T> clazz;
	private final Ruby runtime;

	public RubyDelegatorFactory(Class<T> clazz, Ruby runtime) {
		this.clazz = clazz;
		this.runtime = runtime;
		getRubyScript(clazz);
	}

	private void getRubyScript(Class<T> clazz) {
		RubyClass annotation = clazz.getAnnotation(RubyClass.class);
		if (annotation == null) {
			throw new IllegalArgumentException("You should annotate your class with @" + RubyClass.class.getName() + " annotation");
		}
		InputStream rubyScriptIs = findRubyScript(annotation);
		String file = readFile(rubyScriptIs);
        runtime.evalScriptlet(file);
	}

	private InputStream findRubyScript(RubyClass annotation) {
		String rubyScript = annotation.value();
		if (!rubyScript.startsWith("/")) {
			rubyScript = "/" + rubyScript;
		}
		InputStream is = getClass().getResourceAsStream(rubyScript);
		if (is == null) {
			throw new IllegalArgumentException("Could not find " + rubyScript + " in the classpath");
		}
		return is;
	}

	public T build() {
		ProxyMaker<T> proxyMaker = new ProxyMaker<>(clazz);
		List<Method> methods = Arrays.asList(clazz.getMethods());
		
		MethodHandler handlerGiven = new RubyDelegatorMethodHandler<T>(methods, runtime, clazz);
		
		T object = proxyMaker.proxyAllMethods(handlerGiven);
		return object;
	}

	
	private String readFile(InputStream rubyScriptIs) {
		Scanner scanner = new Scanner(rubyScriptIs);
		scanner.useDelimiter("$$");
		String script = scanner.next();
		return script;
	}
}
