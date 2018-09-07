package main.java.com.arduino.sml;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.logging.log4j.util.Chars;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private static final String EM_DASH = String.valueOf((char) 0x2014);

    @GetMapping
    String index(Model model) {
        model.addAttribute("msg", "Home Page");
        return "home";
    }

    @GetMapping("/ports")
    String getPorts(Model model) {
        var ports = SerialPort.getCommPorts();
        var columnsOfPorts = Arrays.stream(ports).map(this::serialPortToList).collect(Collectors.toList());
        model.addAttribute("columnsOfPorts", columnsOfPorts);
        return "api/ports";
    }

    @GetMapping("/write/{text}")
    void writeToFile(@PathVariable String text) throws IOException {
        var timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm.ss0"));
        Path path = Paths.get("C:\\Users\\Olgerd\\Desktop\\outputFolder\\output_file.txt");
        if (Files.exists(path)) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
                writer.newLine();
                writer.write(timeStamp+":"+ Chars.TAB);
                writer.write(text);
            }
        } else {
            Files.write(path, text.getBytes());
        }
    }

    @GetMapping("/io")
    public String getIOStreams(Model model){
        var serialPort = SerialPort.getCommPorts()[1];
        var input = serialPort.getInputStream() != null ? new BufferedReader(new InputStreamReader(serialPort.getInputStream())) : EM_DASH;
        var output = serialPort.getOutputStream() != null ? serialPort.getOutputStream() : EM_DASH;
        model.addAttribute("input", input);
        model.addAttribute("output", output);
        return "api/io";
    }

    private List<String> serialPortToList(SerialPort serialPort) {
        return List.of(
                "PortDescription:" + serialPort.getPortDescription(),
                "SystemPortName:" + serialPort.getSystemPortName(),
                "BaudRate: " + serialPort.getBaudRate(),
                "Open:" + serialPort.isOpen(),
                "NumDataBits:" + serialPort.getNumDataBits(),
                "NumStopBits:" + serialPort.getNumStopBits()
        );

    }
}