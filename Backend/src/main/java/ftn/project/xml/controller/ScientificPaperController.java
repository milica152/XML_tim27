package ftn.project.xml.controller;

import ftn.project.xml.dto.MetadataDTO;
import ftn.project.xml.dto.ScientificPaperDTO;
import ftn.project.xml.model.ScientificPaper;
import ftn.project.xml.service.ScientificPaperService;
import ftn.project.xml.util.AuthenticationUtilities;
import ftn.project.xml.util.RDFAuthenticationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.xmldb.api.base.XMLDBException;
import sun.misc.Request;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping(value = "/scientificPaper")
public class ScientificPaperController {

    @Autowired
    private ScientificPaperService scientificPaperService;

    @PreAuthorize("hasAnyRole('AUTHOR','EDITOR','REVIEWER')")
    @PostMapping("/save")
    @ResponseBody
    public ResponseEntity<String> saveScientificPaper(@RequestBody String xmlRes) throws Exception {
        return new ResponseEntity<>(scientificPaperService.save(AuthenticationUtilities.loadProperties(), xmlRes), HttpStatus.OK);
    }

    @GetMapping("/findByTitle")
    @PreAuthorize("(hasAuthority('AUTHOR'))")
    @ResponseBody
    public ResponseEntity<String> getScientificPaperById(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getByTitle(AuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @GetMapping("/findByTitleToHTML")
    @PreAuthorize("(hasAuthority('AUTHOR'))")
    @ResponseBody
    public ResponseEntity<String> getScientificPaperByIdHTML(@RequestParam("title") String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.transformToHTML(scientificPaperService.getByTitle(AuthenticationUtilities.loadProperties(), title)), HttpStatus.OK);
    }

    @GetMapping("/search")
    @PreAuthorize("(hasAuthority('AUTHOR'))")
    @ResponseBody
    public ResponseEntity<List<ScientificPaperDTO>> search(@RequestParam("author") String author, @RequestParam("title") String title, @RequestParam("keyword") String keyword) throws Exception {
        return new ResponseEntity<>(scientificPaperService.search(AuthenticationUtilities.loadProperties(), author, title, keyword), HttpStatus.OK);
    }


    @GetMapping("/findMyPapers")
    @PreAuthorize("(hasAuthority('AUTHOR'))")
    @ResponseBody
    public ResponseEntity<List<String>> findUserPapers() throws IOException, ClassNotFoundException, InstantiationException, XMLDBException, IllegalAccessException {
        return new ResponseEntity<>(scientificPaperService.findMyPapers(AuthenticationUtilities.loadProperties()), HttpStatus.OK);
    }


    @GetMapping("/metadata")
    @ResponseBody
    public ResponseEntity<List<MetadataDTO>> getMetadata(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.getMetadata(RDFAuthenticationUtilities.loadProperties(), title), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    @PostMapping("/exportMetadataRDF/{title}")
    @ResponseBody
    public ResponseEntity<String> exportMetadataAsRDF(@RequestBody String filePath, @PathVariable String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.exportMetadataToRDF(AuthenticationUtilities.loadProperties(), title, filePath), HttpStatus.OK);
    }



    @PreAuthorize("(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    @PostMapping("/exportMetadataJSON/{title}")
    @ResponseBody
    public ResponseEntity<String> exportMetadataAsJSON(@RequestBody String filePath, @PathVariable String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.exportMetadataToJSON(RDFAuthenticationUtilities.loadProperties(), title, filePath), HttpStatus.OK);
    }

    @PreAuthorize("(hasAuthority('EDITOR'))")
    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.delete(title, AuthenticationUtilities.loadProperties()), HttpStatus.OK);
    }

    @PutMapping("/transformHTML")
    @PreAuthorize("(hasAuthority('AUTHOR') or hasAuthority('REVIEWER') or hasAuthority('EDITOR'))")
    @ResponseBody
    public ResponseEntity transformToHtml(@RequestBody String xml) throws Exception {
        scientificPaperService.transformToHTML(xml);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/withdraw")
    @ResponseBody
    public ResponseEntity<String> withdraw(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.withdraw(AuthenticationUtilities.loadProperties(),  title), HttpStatus.OK);
    }


    @PostMapping("/accept")
    @ResponseBody
    public ResponseEntity<String> accept(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.accept(AuthenticationUtilities.loadProperties(),  title), HttpStatus.OK);
    }


    @PostMapping("/reject")
    @ResponseBody
    public ResponseEntity<String> reject(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(scientificPaperService.reject(AuthenticationUtilities.loadProperties(),  title), HttpStatus.OK);
    }

}
