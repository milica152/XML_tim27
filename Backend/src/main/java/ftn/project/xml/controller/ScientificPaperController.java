package ftn.project.xml.controller;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.RDFAuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(allowedHeaders = "http://localhost:4200/")
@RequestMapping(value = "/scientificPaper")
public class ScientificPaperController {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @PostMapping("/save/{revision}/{title}")
    @ResponseBody
    public ResponseEntity<String> saveScientificPaper(@RequestBody String xmlRes, @PathVariable String revision, @PathVariable String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.save(AuthenticationUtilities.loadProperties(), xmlRes, Boolean.parseBoolean(revision), title), HttpStatus.OK);
    }

    @GetMapping("/findByTitle")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<String> getScientificPaperById(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getByTitle(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @GetMapping("/findByTitleNoMetadata")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<String> findByTitleNoMetadata(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getByTitleNoMetadata(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }



    @PostMapping("/findByTitleWithoutAuthors")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<String> findByTitleWithoutAuthors(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getByTitleWithoutAuthors(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }


    @GetMapping("/findByTitleToHTML")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<String> getScientificPaperByIdHTML(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.transformToHTML(scientificPaperService.getByTitle(AuthenticationUtilities.loadProperties(), title)), HttpStatus.OK);
    }

    @GetMapping("/advancedSearch")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    public ResponseEntity<List<String>> advancedSearch(@RequestParam("status") String status, @RequestParam("title")
            String title, @RequestParam("published") String published, @RequestParam("authorsName") String authorsName,
            @RequestParam("keywords") String keywords,@RequestParam("accepted") String accepted, @RequestParam("author") String author) throws Exception {
        return new ResponseEntity<>(scientificPaperService.advancedSearch(AuthenticationUtilities.loadProperties(), status,
                title, published, authorsName, keywords, accepted, author), HttpStatus.OK);
    }

    @GetMapping("/search")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    public ResponseEntity<List<String>> search(@RequestParam("author") String author, @RequestParam("text") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.search(AuthenticationUtilities.loadProperties(), author, title), HttpStatus.OK);
    }


    @GetMapping("/findMyPapers")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<List<String>> findUserPapers() throws IOException, ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return new ResponseEntity<>(scientificPaperService.findMyPapers(AuthenticationUtilities.loadProperties()), HttpStatus.OK);
    }

    @GetMapping("/findAllPapers")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<List<String>> getAllPapers() throws IOException {
        return new ResponseEntity<>(scientificPaperService.getAllPapers(AuthenticationUtilities.loadProperties()), HttpStatus.OK);
    }

    @GetMapping("/metadata")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<List<MetadataDTO>> getMetadata(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getMetadata(RDFAuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @PostMapping("/exportMetadataRDF/{title}")
    @ResponseBody
    public ResponseEntity<String> exportMetadataAsRDF(@RequestBody String filePath, @PathVariable String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.exportMetadataToRDF(AuthenticationUtilities.loadProperties(), title, filePath), HttpStatus.OK);
    }



    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @PostMapping("/exportMetadataJSON/{title}")
    @ResponseBody
    public ResponseEntity<String> exportMetadataAsJSON(@RequestBody String filePath, @PathVariable String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.exportMetadataToJSON(RDFAuthenticationUtilities.loadProperties(), title, filePath), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.delete(title, AuthenticationUtilities.loadProperties()), HttpStatus.OK);
    }

    @PutMapping("/transformHTML")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity transformToHtml(@RequestBody String xml) throws Exception {
        scientificPaperService.transformToHTML(xml);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    public ResponseEntity<String> withdraw(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.withdraw(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @PostMapping("/accept")
    @ResponseBody
    public ResponseEntity<String> accept(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.accept(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @PostMapping("/reject")
    @ResponseBody
    public ResponseEntity<String> reject(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.reject(AuthenticationUtilities.loadProperties(),  title), HttpStatus.OK);
    }


    @GetMapping("/getStatus")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    public ResponseEntity<String> getStatus(@RequestParam("paper") String paper) throws Exception {
        AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
        return new ResponseEntity<>(scientificPaperService.getStatus(conn, paper), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @GetMapping("/checkIfReviewed")
    @ResponseBody
    public ResponseEntity<Boolean> checkIfReviewed(@RequestParam("title") String title) throws Exception{
        return new ResponseEntity<>(scientificPaperService.checkIfReviewed(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @PostMapping("/requestRevision")
    @ResponseBody ResponseEntity requestRevision(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.requestRevision(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

}
