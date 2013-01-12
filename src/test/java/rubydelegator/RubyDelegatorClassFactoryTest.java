package rubydelegator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jruby.Ruby;
import org.junit.Before;
import org.junit.Test;

import rubydelegator.jruby.RubyDelegatorFactory;
import rubydelegator.jruby.RubyMethod;

public class RubyDelegatorClassFactoryTest {
	
	private Ruby runtime;

	@Before
	public void setUp() {
		runtime = Ruby.newInstance();
	}
	
	@Test
	public void shouldBuildClassWithNoMethodToDelagate() throws Exception {
		RubyDelegatorFactory<NoMethodToDelegate> rubyDelegatorClass = new RubyDelegatorFactory<NoMethodToDelegate>(NoMethodToDelegate.class, runtime);
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
		RubyDelegatorFactory<RubyClazz> rubyDelegatorClass = new RubyDelegatorFactory<RubyClazz>(RubyClazz.class, runtime);
		RubyClazz object = rubyDelegatorClass.build();
		assertEquals(42, object.method());
		assertEquals("42", object.string());
		assertNotNull(object.list());
		
		assertEquals(100, object.normalMethod());
	}
	
	@RubyClass("ruby_clazz.rb") //contains RubyClazz#method which returns 42 and RubyClazz#string which returns "42"  
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
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionWhenClassIsNotAnnotated() {
		new RubyDelegatorFactory<NotAnnotated>(NotAnnotated.class, runtime);
	}
	
	public static class NotAnnotated {
		@RubyMethod
		public int method() {
			return 0;
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionWhenClassIsAnnotatedWithUnexistentScript() {
		new RubyDelegatorFactory<UnexistentScript>(UnexistentScript.class, runtime);
	}
	
	@RubyClass("unexistent.rb")
	public static class UnexistentScript {
		@RubyMethod
		public int method() {
			return 0;
		}
	}
	
}
