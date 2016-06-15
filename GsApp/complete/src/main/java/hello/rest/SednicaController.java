package hello.rest;

import hello.util.SednicaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PreAuthorize("hasRole('ROLE_PREDSEDNIK')")
    @RequestMapping("/startstopsednica/{sednicaSwitch}")
    public HttpStatus startStopSednica(@PathVariable boolean sednicaSwitch) {
        logger.info("Called REST for starting conference");

        SednicaManager sm=new SednicaManager();
        sm.updateSednica(sednicaSwitch);
        return HttpStatus.OK;
    }

    @RequestMapping("/getsednicastatus")
    public ResponseEntity getSednicaStatus() {
        logger.info("Called REST for getting conference status");

        return new ResponseEntity(new SednicaManager().getSednicaStatus(), HttpStatus.OK);
    }
}