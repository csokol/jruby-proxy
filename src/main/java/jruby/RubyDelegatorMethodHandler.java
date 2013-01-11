package jruby;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.experimental.theories.internal.AllMembersSupplier;

import javassist.util.proxy.MethodHandler;

public class RubyDelegatorMethodHandler implements MethodHandler {

	private ArrayList<Method> methodsToProxy;
	private String scriptContent;

	public RubyDelegatorMethodHandler(List<Method> allMethods,
			InputStream scriptStream) {
		this.scriptContent = readScriptContent(scriptStream);
		this.methodsToProxy = new RubyMethodsFinder().find(allMethods);
	}

	@Override
	public Object invoke(Object self, Method thisMethod, Method proceed,
			Object[] args) throws Throwable {
		if (methodsToProxy.contains(thisMethod)) {
			return 42;
		}
		return proceed.invoke(self, args);
	}


	private String readScriptContent(InputStream scriptStream) {
		Scanner scanner = new Scanner(scriptStream);
		scanner.useDelimiter("$$");
		String content = scanner.next();
		return content;
	}

}
