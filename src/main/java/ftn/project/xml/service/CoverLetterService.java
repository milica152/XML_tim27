package ftn.project.xml.service;

import ftn.project.xml.repository.CoverLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoverLetterService {

    @Autowired
    private CoverLetterRepository coverLetterRepository;



}
