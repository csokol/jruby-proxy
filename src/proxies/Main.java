package proxies;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;

class ToBeProxied {
	public void method() {
		System.out.println("inside method");
	}
}

public class Main {

	public static void main(String[] args) throws NotFoundException,
			CannotCompileException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class proxyClass = proxyClass();
		Object proxied = proxyClass.newInstance();
		ToBeProxied.class.getMethod("method").invoke(proxied);
	}

	private static Class proxyClass() throws NotFoundException,
			CannotCompileException {
		CtClass classToBeProxied = ClassPool.getDefault().get(
				"proxies.ToBeProxied");
		List<CtMethod> methods = Arrays.asList(classToBeProxied.getDeclaredMethods());
		for (CtMethod methodToBeProxied : methods) {
			String name = methodToBeProxied.getName();
			CtMethod newMethod = makeProxiedMethod(methodToBeProxied, name, classToBeProxied);
			methodToBeProxied.setName(name + "$");
			
			classToBeProxied.addMethod(newMethod);
		}
		return classToBeProxied.toClass();
	}

	private static CtMethod makeProxiedMethod(CtMethod methodToBeProxied, String originalName, CtClass classToBeProxied)
			throws NotFoundException, CannotCompileException {
		CtClass[] params = {};
		CtMethod newMethod = new CtMethod(CtClass.voidType, originalName, params, classToBeProxied);
		
		newMethod.setBody("{System.out.println(\"rá! glu glu!\");return;}");
		return newMethod;
	}
}
