package rubydelegator;

import java.io.InputStream;
import java.util.Scanner;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

public class Testes {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Ruby runtime = Ruby.newInstance();
		long end = System.currentTimeMillis();
		
		String file = readFile();
        IRubyObject res = runtime.evalScriptlet(file);
        res = runtime.evalScriptlet("RubyClass.new");
        System.out.println(res.isClass());
        System.out.println(res);
		res.callMethod(runtime.getCurrentContext(), "method");
		System.out.println(end - start);
	}

	private static String readFile() {
		InputStream stream = Testes.class.getResourceAsStream("/ruby_class.rb");
		Scanner scanner = new Scanner(stream);
		scanner.useDelimiter("$$");
		String script = scanner.next();
		return script;
	}

}
