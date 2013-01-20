package rubydelegator.jruby.converter;

import static org.junit.Assert.*;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;
import org.junit.Before;
import org.junit.Test;

public class RubyTypeConverterTest {

	private Ruby runtime;
	private RubyTypeConverter rubyTypeConverter;
	
	@Before
	public void setUp() {
		runtime = Ruby.newInstance();
		rubyTypeConverter = new RubyTypeConverter(runtime);
	}

	@Test
	public void shouldConvertNumbers() {
		IRubyObject obj = rubyTypeConverter.convert(new Integer(100));
		assertEquals("Fixnum", obj.getType().toString());
		obj = rubyTypeConverter.convert(1.10);
		assertEquals("Float", obj.getType().toString());
		obj = rubyTypeConverter.convert(100l);
		assertEquals("Fixnum", obj.getType().toString());
	}
	
	@Test
	public void shouldConvertString() {
		IRubyObject obj = rubyTypeConverter.convert("string");
		assertEquals("String", obj.getType().toString());
	}
	
	@Test
	public void shouldConvertArray() {
		Object[] objects = {"lala"};
		IRubyObject obj = rubyTypeConverter.convert(objects);
		assertEquals("Array", obj.getType().toString());
	}

}
