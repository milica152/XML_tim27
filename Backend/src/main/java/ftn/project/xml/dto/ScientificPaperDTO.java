package ftn.project.xml.dto;

import java.util.ArrayList;
import java.util.List;

public class ScientificPaperDTO {
    private String title;
    private List<String> keywords  = new ArrayList<>();
    private List<String> authors = new ArrayList<>();

    public ScientificPaperDTO(String title, List<String> keywords, List<String> authors) {
        this.title = title;
        this.keywords = keywords;
        this.authors = authors;
    }

    public ScientificPaperDTO() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
