package rubydelegator.jruby.converter;

public class StringConverter extends AbstractObjectToScriptlet implements ObjectToScriptlet {

	private final ObjectToScriptlet next;

	public StringConverter(ObjectToScriptlet next) {
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
	public ObjectToScriptlet getDelegate() {
		return next;
	}


}
