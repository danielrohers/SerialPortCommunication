package serial.port.communication;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 *
 * @author Daniel Röhers Moura
 */
public class ManupulationSerialPort implements Runnable, SerialPortEventListener {

    private Thread thread;

    /**
     *
     * @param outputStreamPort
     * @param commandToPort
     */
    public void writePort(OutputStream outputStreamPort, String commandToPort) {
        try {
            outputStreamPort.write(commandToPort.getBytes());
            Thread.sleep(100);
            outputStreamPort.flush();
        } catch (IOException ex) {
            exceptionMessageConsole(ex);
        } catch (InterruptedException ex) {
            exceptionMessageConsole(ex);
        }
    }

    /**
     *
     * @param inputStreamPort
     * @return
     */
    public String readPort(InputStream inputStreamPort) {
        StringBuffer message = new StringBuffer();
        try {
            while (inputStreamPort.read() != -1) {
                if ('\r' == (char) inputStreamPort.read()) {
                    message.append("\n");
                } else {
                    message.append((char) inputStreamPort.read());
                }
            }
        } catch (IOException ex) {
            exceptionMessageConsole(ex);
        }
        return message != null ? message.toString() : "";
    }

    /**
     *
     * @param serialPort
     * @return
     */
    public InputStream getInputPort(SerialPort serialPort) {
        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            thread = new Thread(this);
            thread.start();
            run();
            return serialPort.getInputStream();
        } catch (IOException ex) {
            exceptionMessageConsole(ex);
            return null;
        } catch (TooManyListenersException ex) {
            exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     *
     * @param serialPort
     * @return
     */
    public OutputStream getOutputPort(SerialPort serialPort) {
        try {
            return serialPort.getOutputStream();
        } catch (IOException ex) {
            exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     *
     * @param portName
     * @return
     */
    public CommPortIdentifier getPortId(String portName) {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            return portIdentifier;
        } catch (NoSuchPortException e) {
            exceptionMessageConsole(e);
            return null;
        }
    }

    /**
     *
     * @param commPortIdentifier
     * @param baurate
     * @param timeout
     * @return
     */
    public SerialPort openPort(CommPortIdentifier commPortIdentifier, int baurate, int timeout) {
        try {
            SerialPort serialPort = (SerialPort) commPortIdentifier.open(this.getClass().getName(), timeout);
            serialPort.setSerialPortParams(baurate, serialPort.DATABITS_8, serialPort.STOPBITS_1, serialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            return serialPort;
        } catch (PortInUseException ex) {
            exceptionMessageConsole(ex);
            return null;
        } catch (UnsupportedCommOperationException ex) {
            exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     *
     * @param serialPort
     * @return
     */
    public boolean closePort(SerialPort serialPort) {
        try {
            serialPort.close();
            return true;
        } catch (Exception e) {
            exceptionMessageConsole(e);
            return false;
        }
    }

    /**
     *
     * @param exception
     */
    private static void exceptionMessageConsole(Exception exception) {
        System.out.println("Message: " + exception.getMessage());
        System.out.println("Cause: " + exception.getCause());
        exception.printStackTrace();
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            exceptionMessageConsole(ex);
        }
    }
}
