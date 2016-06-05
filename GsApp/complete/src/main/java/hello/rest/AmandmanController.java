package hello.rest;

import hello.businessLogic.document.AmandmanManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Nebojsa on 6/5/2016.
 */
@RestController
@RequestMapping("/api")
public class AmandmanController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AmandmanManager amandmanManager = new AmandmanManager();

    @RequestMapping(value = "/getallamandmans",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAmandmans() {
        ArrayList<Amandman> amandmani = amandmanManager.getAllAmendmentProposed();
        return new ResponseEntity(amandmani, HttpStatus.OK);
    }

    @RequestMapping(value = "/amandman",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity postAmandman(@RequestBody Amandman amandman){
        logger.info("[CONTENT OF ADDED AMANDMAN]:"+amandman.toString());

        if(!amandmanManager.validateAkt(amandman)){
            logger.info("[AmandmanController] ERROR: NOT VALIDATED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String docID = amandmanManager.proposeAmandman(amandman);
        if(docID==null){
            logger.info("[AmandmanController] ERROR: NOT PROPOSED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            logger.info("[AmandmanController] Successfully proposed amandman: " + amandman.toString() + " with id " + docID);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
