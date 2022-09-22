package lapr2.project.agpsd.utils;

public class OutOfBoundArgumentException extends IllegalArgumentException {

    public OutOfBoundArgumentException() {
         super("Out of bound argument!");
    }
    
    public OutOfBoundArgumentException(String message) {
         super(message);
    }
    
}
