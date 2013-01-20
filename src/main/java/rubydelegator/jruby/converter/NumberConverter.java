package rubydelegator.jruby.converter;


public class NumberConverter extends AbstractObjectToScriptlet {

	private final ObjectToScriptlet next;

	public NumberConverter(ObjectToScriptlet next) {
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
	public ObjectToScriptlet getDelegate() {
		return next;
	}

}
