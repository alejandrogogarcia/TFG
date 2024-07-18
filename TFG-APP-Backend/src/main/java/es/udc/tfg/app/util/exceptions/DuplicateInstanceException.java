package es.udc.tfg.app.util.exceptions;

public class DuplicateInstanceException extends Exception{

    private Object instanceId;
    private String className;

    public DuplicateInstanceException(Object instanceId, String className) {
        super("Duplicate Instance (instanceId = '" + instanceId + "' & className = '" + className + "')");
        this.instanceId = instanceId;
        this.className = className;
    }

    public Object getInstanceId() {
        return instanceId;
    }

    public String getClassName() {
        return className;
    }
}
