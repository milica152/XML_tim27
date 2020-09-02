package ftn.project.xml.service;

import ftn.project.xml.model.BusinessProcess;
import ftn.project.xml.model.ReviewsGradeType;
import ftn.project.xml.model.StatusEnum;
import ftn.project.xml.repository.BusinessProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.base.XMLDBException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigInteger;

@Service
public class BusinessProcessService {
    @Autowired
    BusinessProcessRepository businessProcessRepository;

    public String createBusinessProcess(String title) throws Exception {
        BusinessProcess businessProcess = new BusinessProcess();
        businessProcess.setPaperTitle(title);
        businessProcess.setStatus(StatusEnum.SUBMITTED);
        businessProcess.setReviewsGrade(new ReviewsGradeType());
        businessProcess.setVersion(BigInteger.valueOf(1));
        return businessProcessRepository.save(businessProcess, title);
    }

    // za menjanje business process-a, samo se getuje BP po title-u, izmeni kao objekat i ponovo upise u bazu

    public BusinessProcess findByScientificPaperTitle (String title) throws IllegalAccessException, InstantiationException, JAXBException, IOException, XMLDBException, ClassNotFoundException {
        return businessProcessRepository.getByScientificPaperTitle(title);
    }


    public void save(BusinessProcess businessProcess) throws IllegalAccessException, IOException, JAXBException, InstantiationException, XMLDBException, ClassNotFoundException {
        businessProcessRepository.save(businessProcess, businessProcess.getPaperTitle());
    }
}
