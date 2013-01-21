package rubydelegator.acceptance;

import static org.junit.Assert.assertEquals;

import org.jruby.Ruby;
import org.junit.Test;

import rubydelegator.TypeConverter;
import rubydelegator.annotation.RubyClass;
import rubydelegator.annotation.RubyMethod;
import rubydelegator.jruby.RubyDelegatorFactory;
import rubydelegator.jruby.RubyMethodHandler;
import rubydelegator.jruby.converter.RubyTypeConverter;


public class RubyTest {

	@Test
	public void shouldCallMethodsFromScript() throws Exception {
		Ruby runtime = Ruby.newInstance();
		TypeConverter converter = new RubyTypeConverter(runtime);
		RubyMethodHandler<Acceptance> handler = new RubyMethodHandler<>(runtime, Acceptance.class, converter);
		
		RubyDelegatorFactory<Acceptance> factory = new RubyDelegatorFactory<>(Acceptance.class, runtime, handler);
		Acceptance object = factory.build();
		object.method1();
		
		int square = object.method2(10);
		assertEquals(100, square);
		
		String result = object.method3("some string");
		assertEquals("some string appended by ruby", result);
		
		Object[] array = {"blabla", null};
		int size = object.method4(array);
		assertEquals(2, size);
	}
	
	@RubyClass("acceptance.rb")
	public abstract static class Acceptance {
		@RubyMethod
		public abstract void method1();
		@RubyMethod
		public abstract int method2(int param);
		@RubyMethod
		public abstract String method3(String string);
		@RubyMethod
		public abstract int method4(Object[] array);
	}
}
