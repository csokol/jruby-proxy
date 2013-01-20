package rubydelegator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jruby.Ruby;
import org.junit.Before;
import org.junit.Test;

import rubydelegator.annotation.RubyClass;
import rubydelegator.annotation.RubyMethod;
import rubydelegator.jruby.RubyDelegatorFactory;
import rubydelegator.jruby.RubyMethodHandler;
import rubydelegator.jruby.TypeConverter;

public class RubyDelegatorClassFactoryTest {
	
	private Ruby runtime;

	@Before
	public void setUp() {
		runtime = Ruby.newInstance();
	}
	
	@Test
	public void shouldBuildClassWithNoMethodToDelagate() throws Exception {
		RubyDelegatorFactory<NoMethodToDelegate> rubyDelegatorClass = buildFactory(NoMethodToDelegate.class);
		NoMethodToDelegate object = rubyDelegatorClass.build();
		assertEquals(42, object.method());
	}
	
	@RubyClass("no_method_to_delegate.rb")
	public static class NoMethodToDelegate {
		public int method() {
			return 42;
		}
	}
	
	@Test
	public void shouldBuildClassAndUseRubyImpl() throws Exception {
		RubyDelegatorFactory<RubyClazz> rubyDelegatorClass = buildFactory(RubyClazz.class);
		RubyClazz object = rubyDelegatorClass.build();
		assertNotNull(object);
		assertEquals(42, object.method());
		assertEquals("42", object.string());
		assertNotNull(object.list());
		
		assertEquals(100, object.normalMethod());
	}
	
	@Test
	public void shouldCallMethodWithParameters() throws Exception {
		TypeConverter mockedConverter = mock(TypeConverter.class);
		when(mockedConverter.convert(new Integer(100))).thenReturn(runtime.evalScriptlet("100"));
		RubyDelegatorFactory<RubyClazz> rubyDelegatorClass = buildFactory(RubyClazz.class, mockedConverter);
		RubyClazz object = rubyDelegatorClass.build();
		assertEquals(100*100, object.withParameter(100));
	}

	
	@RubyClass("ruby_clazz.rb") //contains class RubyClazz class  
	public static class RubyClazz {
		@RubyMethod
		public int method() {
			return 0;
		}
		@RubyMethod
		public String string() {
			return "0";
		}
		
		@RubyMethod
		public List<Integer> list() {
			return null;
		}
		
		public int normalMethod() {
			return 100;
		}
		
		@RubyMethod
		public int withParameter(int param) {
			return param;
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionWhenClassIsNotAnnotated() {
		buildFactory(NotAnnotated.class);
	}
	
	public static class NotAnnotated {
		@RubyMethod
		public int method() {
			return 0;
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionWhenClassIsAnnotatedWithUnexistentScript() {
		buildFactory(UnexistentScript.class);
	}
	
	@RubyClass("unexistent.rb")
	public static class UnexistentScript {
		@RubyMethod
		public int method() {
			return 0;
		}
	}
	
	private <T>RubyDelegatorFactory<T> buildFactory(Class<T> clazz) {
		return buildFactory(clazz, null);
	}
	
	private <T>RubyDelegatorFactory<T> buildFactory(Class<T> clazz, TypeConverter converter) {
		RubyMethodHandler<T> rubyMethodHandler = new RubyMethodHandler<T>(runtime, clazz, converter);
		RubyDelegatorFactory<T> rubyDelegatorClass = new RubyDelegatorFactory<T>(clazz, runtime, rubyMethodHandler);
		return rubyDelegatorClass;
	}
}
