import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.InputStream;

public class SerialMonitorLogger {
    static public void main(String[] args)
        {
            SerialPort comPort = SerialPort.getCommPorts()[2];
            comPort.openPort();
            comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0);
            InputStream in = comPort.getInputStream();
            try
            {
                while (true) {
                    System.out.print((char)in.read());
                    in.close();
                }
            } catch (Exception e) { e.printStackTrace(); }
            comPort.closePort();
    }
}
