package jruby;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

public class RubyDelegatorClassFactoryTest {
	
	@Test
	public void shouldBuildClassWithNoMethodToDelagate() throws Exception {
		RubyDelegatorClass<NoMethodToDelegate> rubyDelegatorClass = new RubyDelegatorClass<NoMethodToDelegate>(NoMethodToDelegate.class);
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
		RubyDelegatorClass<RubyClazz> rubyDelegatorClass = new RubyDelegatorClass<RubyClazz>(RubyClazz.class);
		RubyClazz object = rubyDelegatorClass.build();
		assertEquals(42, object.method());
		assertEquals("42", object.string());
	}
	
	@RubyClass("ruby_class.rb") //contains RubyClass#method which returns 42 and RubyClass#string which returns "42"  
	public static class RubyClazz {
		@RubyMethod
		public int method() {
			return 0;
		}
		@RubyMethod
		public String string() {
			return "0";
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionWhenClassIsNotAnnotated() {
		new RubyDelegatorClass<NotAnnotated>(NotAnnotated.class);
	}
	
	public static class NotAnnotated {
		@RubyMethod
		public int method() {
			return 0;
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowExceptionWhenClassIsAnnotatedWithUnexistentScript() {
		new RubyDelegatorClass<UnexistentScript>(UnexistentScript.class);
	}
	
	@RubyClass("unexistent.rb")
	public static class UnexistentScript {
		@RubyMethod
		public int method() {
			return 0;
		}
	}

}
