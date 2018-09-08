package com.dyadic;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class SerialMonitorLogger {
    static public void main(String[] args) {
        var comPorts = SerialPort.getCommPorts();
        Optional<SerialPort> port = Arrays.stream(comPorts)
                .filter(serialPort -> serialPort.getDescriptivePortName()
                        .equals("USB-SERIAL CH340 (COM6)")).findFirst();
        var comPort = port.orElse(null);
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 10000, 0);

        InputStream in = comPort.getInputStream();
        try {
            ArrayList<Character> charCodes = new ArrayList<>();
            while (true) {
                char readChar = (char) in.read();
                System.out.print(readChar);
                charCodes.add(readChar);
                if (readChar == '\n' || charCodes.size() > 100) {
                    writeToFile(convertToString(charCodes));
                    charCodes.clear();
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();
    }

    static private void writeToFile(String text) throws IOException {
        var timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm.ss0"));
        Path path = Paths.get("C:\\Users\\Olgerd\\Desktop\\output_file.txt");
        if (Files.exists(path)) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.newLine();
                writer.write(timeStamp + ":" + '\t');
                writer.write(text);
            }
        } else {
            Files.write(path, text.getBytes());
        }
    }

    private static String convertToString(ArrayList<Character> arrayList) {
        return arrayList.
                stream()
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
