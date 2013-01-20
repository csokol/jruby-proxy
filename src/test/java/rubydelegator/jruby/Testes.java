package rubydelegator.jruby;

import java.io.InputStream;
import java.util.Scanner;

import org.jruby.Ruby;
import org.jruby.RubyBasicObject;
import org.jruby.runtime.builtin.IRubyObject;

public class Testes {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Ruby runtime = Ruby.newInstance();
		long end = System.currentTimeMillis();
		
		String file = readFile();
        IRubyObject res = runtime.evalScriptlet(file);
        res = runtime.evalScriptlet("RubyClazz.new");
        IRubyObject[] rubyargs = new IRubyObject[1];
        rubyargs[0] = runtime.evalScriptlet("100");
		IRubyObject out = res.callMethod(runtime.getCurrentContext(), "with_arg", rubyargs);
		System.out.println(out);
		System.out.println(out.getJavaClass());
		System.out.println(end - start);
	}

	private static String readFile() {
		InputStream stream = Testes.class.getResourceAsStream("/ruby_clazz.rb");
		Scanner scanner = new Scanner(stream);
		scanner.useDelimiter("$$");
		String script = scanner.next();
		return script;
	}

}
