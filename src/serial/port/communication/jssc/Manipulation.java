package serial.port.communication.jssc;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import serial.port.communication.useful.Useful;

/**
 *
 * @author Daniel Röhers Moura
 */
public class Manipulation {
    
    private int lenghtMessageSerialPort;

    /**
     * Checking existence of the door
     * @param port
     * @return 
     */
    public boolean existPort(String port) {
        try {
            SerialPort serial;
            serial = new SerialPort(port);
            return true;
        } catch (Exception ex) {
            Useful.exceptionMessageConsole(ex);
            return false;
        }
    }

    /**
     * List all serial port
     * @return 
     */
    public String[] listPorts() {
        return SerialPortList.getPortNames();
    }

    /**
     * Get SerialPort by portName
     * @param portName
     * @return 
     */
    public SerialPort getSerialPort(String portName) {
        try {
            return new SerialPort(portName);
        } catch (Exception ex) {
            Useful.exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     * Open serial port
     * @param serialPort
     * @param baurate
     * @param dataBits
     * @param stopBits
     * @param parity
     * @return 
     */
    public SerialPort openSerialPort(SerialPort serialPort, int baurate, int dataBits, int stopBits, int parity) {
        try {
            serialPort.openPort();
            serialPort.setParams(baurate, dataBits, stopBits, parity);
            return serialPort;
        } catch (SerialPortException ex) {
            Useful.exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     * Write serial port
     * @param command
     * @param serialPort 
     */
    public void writeSerialPort(String command, SerialPort serialPort) {
        try {
            serialPort.writeBytes(command.getBytes());
            lenghtMessageSerialPort = command.getBytes().length;
        } catch (SerialPortException ex) {
            Useful.exceptionMessageConsole(ex);
        }
    }

    /**
     * Read serial port
     * @param serialPort
     * @return 
     */
    public String readSerialPort(SerialPort serialPort) {
        try {
            StringBuilder message = new StringBuilder();
            byte[] buffer = serialPort.readBytes(lenghtMessageSerialPort);
            message.append(new String(buffer, "UTF-8"));
            return message.toString();
        } catch (Exception ex) {
            Useful.exceptionMessageConsole(ex);
            return null;
        }
    }

    /**
     * Close serial port
     * @param serialPort
     * @return 
     */
    public boolean closeSerialPort(SerialPort serialPort) {
        try {
            if(serialPort != null){
                serialPort.closePort();
                return true;
            }
            return false;
        } catch (SerialPortException ex) {
            Useful.exceptionMessageConsole(ex);
            return false;
        }
    }
}
