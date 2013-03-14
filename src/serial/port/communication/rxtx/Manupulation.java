package serial.port.communication.rxtx;

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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;
import serial.port.communication.useful.Useful;

/**
 *
 * @author Daniel Röhers Moura
 */
public class Manupulation implements Runnable, SerialPortEventListener {

    private Thread thread;
    
    /**
     * List all ports
     *
     * @return
     */
    public List<String> listPorts() {
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        List<String> listPort = new ArrayList<String>();
        while (portList.hasMoreElements()) {
            CommPortIdentifier commPortIdentifier = (CommPortIdentifier) portList.nextElement();
            listPort.add(commPortIdentifier.getName());
        }
        return listPort;
    }

    /**
     * Checking existence of the door
     *
     * @param port
     * @return
     */
    public boolean existPort(String port) {
        if (port != null && !port.equals("") && !port.trim().equals("")) {
            if (getPortIdByPortName(port) != null) {
                return true;
            }
        }
        return false;
    }

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
            Useful.exceptionMessageConsole(ex);
        } catch (InterruptedException ex) {
            Useful.exceptionMessageConsole(ex);
        }
    }

    /**
     * Read port
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
            Useful.exceptionMessageConsole(ex);
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
            Useful.exceptionMessageConsole(ex);
            return null;
        } catch (TooManyListenersException ex) {
            Useful.exceptionMessageConsole(ex);
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
            Useful.exceptionMessageConsole(ex);
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
            Useful.exceptionMessageConsole(e);
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
            Useful.exceptionMessageConsole(ex);
            return null;
        } catch (UnsupportedCommOperationException ex) {
            Useful.exceptionMessageConsole(ex);
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
            Useful.exceptionMessageConsole(e);
            return false;
        } catch (Exception e) {
            Useful.exceptionMessageConsole(e);
            return false;
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Useful.exceptionMessageConsole(ex);
        }
    }
}
