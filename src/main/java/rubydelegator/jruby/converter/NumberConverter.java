package rubydelegator.jruby.converter;


public class NumberConverter extends AbstractJavaObjectToScriptConverter {

	private final JavaObjectToScriptConverter next;

	public NumberConverter(JavaObjectToScriptConverter next) {
		this.next = next;
	}
	
	@Override
	public boolean canConvert(Object javaObject) {
		return Number.class.isAssignableFrom(javaObject.getClass());
	}
	
	@Override
	public String toScriptlet(Object javaObject) {
		return javaObject.toString();
	}
	
	@Override
	public JavaObjectToScriptConverter getDelegate() {
		return next;
	}

}
