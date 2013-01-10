package proxies;

import static junit.framework.Assert.*;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;

import org.junit.Test;


public class ProxyMakerTest {

	@Test
	public void shouldMakeProxyFromClass() throws Exception {
		
		MethodHandler methodHandler = new MethodHandler() {
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed,
					Object[] args) throws Throwable {
				System.out.println("before!");
				if (thisMethod.getName() == "setX") {
					Object[] args2 = {new Integer(42)};
					args = args2;
				}
				Object result = proceed.invoke(self, args);
				System.out.println("after!");
				return result;
			}
		};
		
		Foo f = new ProxyMaker<Foo>(Foo.class).proxyAllMethods(methodHandler);
		f.setX(100);
		System.out.println(f);
		
		assertEquals(42, f.x);
	}
}