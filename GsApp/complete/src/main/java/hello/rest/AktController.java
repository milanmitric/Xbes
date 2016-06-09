package hello.rest;

import hello.businessLogic.document.AktManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(value = "/akt",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity postAct(@RequestBody Akt akt) {

        logger.info("[CONTENT OF ADDED ACT]:"+akt.toString());

        if(!aktManager.validateAkt(akt)){
            logger.info("[ERROR] NOT VALIDATED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        String docID = aktManager.proposeAkt(akt,user);
        if(docID==null){
            logger.info("[ERROR] NOT PROPOSED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            logger.info("Successfully proposed akt: " + akt.toString() + " with id " + docID);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
