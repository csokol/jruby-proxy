package rubydelegator;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


import org.junit.Test;

import rubydelegator.jruby.RubyMethod;
import rubydelegator.jruby.RubyMethodsFinder;

public class RubyMethodsFinderTest {

	@Test
	public void shouldFindMethods() {
		RubyMethodsFinder rubyMethodsFinder = new RubyMethodsFinder();
		Set<Method> found = rubyMethodsFinder.find(Arrays.asList(SampleClass.class.getMethods()));
		ArrayList<Method> foundList = new ArrayList<>(found);
		assertEquals(1, foundList.size());
		assertEquals("whathever", foundList.get(0).getName());
	}
	
	static class SampleClass {
		@RubyMethod
		public void whathever() {
		}
		
		public void not() {
		}
	}

}
