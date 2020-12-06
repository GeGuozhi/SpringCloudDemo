package ribbon.Controller;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/products")
    public String products() {
        String port = new String();
        try {
            port = restTemplate.getForObject("http://provider1/serviceNumberOne/providerNumberOne",String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "远程调用接口失败,原因未知";
        }
        return port;
    }
}
