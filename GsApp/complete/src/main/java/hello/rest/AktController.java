package hello.rest;

import hello.businessLogic.document.AktManager;
import hello.entity.gov.gradskaskupstina.Akt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Nebojsa on 6/4/2016.
 */
@RestController
@RequestMapping("/api")
public class AktController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AktManager aktManager = new AktManager();

    @RequestMapping(value = "/getallacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAct() {
        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        return new ResponseEntity(aktovi, HttpStatus.OK);
    }

}
