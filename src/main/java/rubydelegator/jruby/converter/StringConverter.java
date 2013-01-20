package rubydelegator.jruby.converter;

public class StringConverter extends AbstractJavaObjectToScriptConverter implements JavaObjectToScriptConverter {

	private final JavaObjectToScriptConverter next;

	public StringConverter(JavaObjectToScriptConverter next) {
		this.next = next;
	}

	@Override
	public boolean canConvert(Object javaObject) {
		return String.class.isAssignableFrom(javaObject.getClass());
	}
	
	@Override
	public String toScriptlet(Object javaObject) {
		return "\"" + javaObject.toString() + "\"";
	}
	
	@Override
	public JavaObjectToScriptConverter getDelegate() {
		return next;
	}

}
