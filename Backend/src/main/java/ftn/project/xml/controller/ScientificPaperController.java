package ftn.project.xml.controller;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.RDFAuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "http://localhost:4200/")
@RequestMapping(value = "/scientificPaper")
public class ScientificPaperController {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveScientificPaper(@RequestBody String xmlRes) throws Exception {
        return new ResponseEntity<>(scientificPaperService.save(AuthenticationUtilities.loadProperties(), xmlRes), HttpStatus.OK);
    }

    @GetMapping("/findByTitle")
    @ResponseBody
    public ResponseEntity<ScientificPaper> getScientificPaperById(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getByTitle(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<ScientificPaperDTO>> search(@RequestParam("author") String author, @RequestParam("title") String title, @RequestParam("keyword") String keyword) throws Exception {
        return new ResponseEntity<>(scientificPaperService.search(AuthenticationUtilities.loadProperties(), author, title, keyword), HttpStatus.OK);
    }


    @GetMapping("/metadata")
    @ResponseBody
    public ResponseEntity<List<MetadataDTO>> getMetadata(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getMetadata(RDFAuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.delete(title, AuthenticationUtilities.loadProperties()), HttpStatus.OK);
    }

    @PutMapping("/transformHTML")
    @ResponseBody
    public ResponseEntity transformToHtml(@RequestBody String xml) throws Exception {
        scientificPaperService.transformToHTML(xml);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
