package serial.port.communication;

import gnu.io.CommPortIdentifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author Daniel Röhers Moura
 */
public class VerifyingSerialPort {

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
            if (new ManupulationSerialPort().getPortIdByPortName(port) != null) {
                return true;
            }
        }
        return false;
    }
}
