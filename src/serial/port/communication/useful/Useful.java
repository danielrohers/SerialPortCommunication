package serial.port.communication.useful;

/**
 *
 * @author Daniel Röhers Moura
 */
public class Useful {
    
    /**
     *
     * @param exception
     */
    public static void exceptionMessageConsole(Exception exception) {
        System.out.println("Message: " + exception.getMessage());
        System.out.println("Cause: " + exception.getCause());
        exception.printStackTrace();
    }
}
