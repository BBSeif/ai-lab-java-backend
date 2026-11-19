package aiproject.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelthController {

    
    @GetMapping("health")
    public String healthCheck() {
        return " OK ";
    }

    
}
