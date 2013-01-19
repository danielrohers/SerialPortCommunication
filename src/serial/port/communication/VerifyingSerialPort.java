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

    public List<String> listPorts() {
        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
        List<String> listPort = new ArrayList<String>();
        while (portList.hasMoreElements()) {
            CommPortIdentifier commPortIdentifier = (CommPortIdentifier) portList.nextElement();
            listPort.add(commPortIdentifier.getName());
        }
        return listPort;
    }

    public boolean existPort(String port) {
        boolean result = false;
        if (port != null) {
            for (String ports : listPorts()) {
                if (port.equals(ports)) {
                    return true;
                }
            }
        }
        return result;
    }
}
