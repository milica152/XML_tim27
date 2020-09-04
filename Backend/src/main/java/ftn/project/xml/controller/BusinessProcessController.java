package ftn.project.xml.controller;


import ftn.project.xml.model.BusinessProcess;
import ftn.project.xml.service.BusinessProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/businessProcess")
public class BusinessProcessController {
    @Autowired
    BusinessProcessService businessProcessService;

    @GetMapping("/findByTitle")
    @PreAuthorize("hasAnyAuthority('AUTHOR','EDITOR','REVIEWER')")
    @ResponseBody
    public ResponseEntity<BusinessProcess> getScientificPaperById(@RequestBody String title) throws Exception {
        return new ResponseEntity<>(businessProcessService.findByScientificPaperTitle(title), HttpStatus.OK);
    }



}
