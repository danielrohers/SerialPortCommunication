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
     * Write port
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
     * Reat port
     *
     * @param inputStreamPort
     * @return
     */
    public String readPort(InputStream inputStreamPort) {
        StringBuilder message = new StringBuilder();
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
        return message.toString();
    }

    /**
     * Get InputStream port
     *
     * @param serialPort
     * @return
     */
    public InputStream getInputStreamPort(SerialPort serialPort) {
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
     * Get OutputStream port
     *
     * @param serialPort
     * @return
     */
    public OutputStream getOutputStreamPort(SerialPort serialPort) {
        try {
            return serialPort.getOutputStream();
        } catch (IOException ex) {
            exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     * Get CommPortIdentifier by port name
     *
     * @param portName
     * @return
     */
    public CommPortIdentifier getPortIdByPortName(String portName) {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            return portIdentifier;
        } catch (NoSuchPortException e) {
            exceptionMessageConsole(e);
            return null;
        }
    }

    /**
     * Open port
     *
     * @param commPortIdentifier
     * @param baurate
     * @param timeout
     * @param dataBits
     * @param stopBits
     * @param parity
     * @param flowControlMode
     * @return
     */
    public SerialPort openPort(CommPortIdentifier commPortIdentifier, int baurate, int timeout,
            int dataBits, int stopBits, int parity, int flowControlMode) {
        try {
            SerialPort serialPort = (SerialPort) commPortIdentifier.open(this.getClass().getName(), timeout);
            serialPort.setSerialPortParams(baurate, dataBits, stopBits, parity);
            serialPort.setFlowControlMode(flowControlMode);
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
     * Close port:
     * Close InputStream, OutputStream and SerialPort
     * @param serialPort
     * @param inputStream
     * @param outputStream
     * @return 
     */
    public boolean closePort(SerialPort serialPort, InputStream inputStream, OutputStream outputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (serialPort != null) {
                serialPort.close();
            }
            return true;
        } catch (IOException e) {
            exceptionMessageConsole(e);
            return false;
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
