package rubydelegator.jruby;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javassist.util.proxy.MethodHandler;

import org.jruby.Ruby;

import rubydelegator.ProxyMaker;
import rubydelegator.annotation.RubyClass;

public class RubyDelegatorFactory<T> {

	private final Class<T> clazz;
	private final Ruby runtime;
	private MethodHandler handlerGiven;

	public RubyDelegatorFactory(Class<T> clazz, Ruby runtime, RubyMethodHandler<T> methodHandler) {
		this.clazz = clazz;
		this.runtime = runtime;
		handlerGiven = methodHandler;
		findAndEvaluateScript(clazz);
		methodHandler.init();
	}

	private void findAndEvaluateScript(Class<T> clazz) {
		RubyClass annotation = clazz.getAnnotation(RubyClass.class);
		if (annotation == null) {
			throw new IllegalArgumentException("You should annotate your class with @" + RubyClass.class.getName() + " annotation");
		}
		InputStream rubyScriptIs = findScript(annotation);
		String file = readFile(rubyScriptIs);
        runtime.evalScriptlet(file);
	}

	private InputStream findScript(RubyClass annotation) {
		String scriptName = annotation.value();
		if (!scriptName.startsWith("/")) {
			scriptName = "/" + scriptName;
		}
		InputStream is = getClass().getResourceAsStream(scriptName);
		if (is == null) {
			throw new IllegalArgumentException("Could not find " + scriptName + " in the classpath");
		}
		return is;
	}

	public T build() {
		ProxyMaker<T> proxyMaker = new ProxyMaker<>(clazz);
		List<Method> methods = Arrays.asList(clazz.getMethods());
		Set<Method> methodsToProxy = new RubyMethodsFinder().find(methods);
		
		T object = proxyMaker.proxy(handlerGiven, methodsToProxy);
		return object;
	}

	
	private String readFile(InputStream scriptIs) {
		Scanner scanner = new Scanner(scriptIs);
		scanner.useDelimiter("$$");
		String script = scanner.next();
		return script;
	}
}
