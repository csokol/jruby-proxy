package jruby;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class RubyMethodsFinderTest {

	@Test
	public void shouldFindMethods() {
		RubyMethodsFinder rubyMethodsFinder = new RubyMethodsFinder();
		ArrayList<Method> found = rubyMethodsFinder.find(Arrays.asList(SampleClass.class.getMethods()));
		assertEquals(1, found.size());
		assertEquals("whathever", found.get(0).getName());
	}
	
	static class SampleClass {
		@RubyMethod
		public void whathever() {
		}
		
		public void not() {
		}
	}

}
