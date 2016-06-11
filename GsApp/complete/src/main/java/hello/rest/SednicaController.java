package hello.rest;

import hello.entity.Sednica;
import hello.util.SednicaManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by aloha on 10-Jun-16.
 */
@RestController
@RequestMapping("/api")
public class SednicaController {

    //TODO - na frontu skloniti 'SEDNICA' za ostale role

    @RequestMapping("/startstopsednica/{sednicaSwitch}")
    public HttpStatus startStopSednica(@PathVariable boolean sednicaSwitch) {
        SednicaManager sm=new SednicaManager();
        sm.updateSednica(sednicaSwitch);
        return HttpStatus.OK;
    }


    @RequestMapping("/getsednicastatus")
    public ResponseEntity getSednicaStatus() {
        return new ResponseEntity(new SednicaManager().getSednicaStatus(), HttpStatus.OK);
    }







}
