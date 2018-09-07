package main.java.com.arduino.sml;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    String index(Model model) {
        model.addAttribute("msg", "Home Page");
        return "home";
    }

    @GetMapping("/ports")
    String getPorts(Model model){
        var ports = SerialPort.getCommPorts();
        var columnsOfPorts = Arrays.stream(ports).map(this::serialPortToList).collect(Collectors.toList());
        model.addAttribute("columnsOfPorts", columnsOfPorts);
        return "api/ports";
    }

    private List<String> serialPortToList(SerialPort serialPort) {
        return List.of("BaudRate: "+serialPort.getBaudRate(),
                "SystemPortName:"+serialPort.getSystemPortName(),
                "PortDescription:"+serialPort.getPortDescription(),
                "Open:"+serialPort.isOpen(),
                "NumDataBits:"+serialPort.getNumDataBits(),
                "NumStopBits:"+serialPort.getNumStopBits()
        );

    }
    //return "redirect: /bookList";
}