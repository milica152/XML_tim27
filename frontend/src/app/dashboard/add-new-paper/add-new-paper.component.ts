import {Component, ElementRef, OnInit, ViewChild, OnDestroy} from '@angular/core';
import {NewScientificPaper} from '../../shared/models/newScientificPaper.model';
import {ScientificPaperService} from '../../core/scientificPaper.service';
import {CoverLetterService} from '../../core/cover-letter.service';
import {XonomyApiService} from "../../core/xonomy-api.service";
declare const Xonomy: any;
import { MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-add-new-paper',
  templateUrl: './add-new-paper.component.html',
  styleUrls: ['./add-new-paper.component.scss']
})
export class AddNewPaperComponent implements OnInit, OnDestroy {

  @ViewChild('editorSP', { static: false }) editorSP: ElementRef;
  @ViewChild('editorCL', { static: false }) editorCL: ElementRef;
  newScientificPaper: Document;
  newScientificPaperTemp: string = '';
  newCoverLetter :string ='';
  paperTitle: string = '';
  metadata: SVGMetadataElement;
  paperPublished: boolean = false;
  coverLetterPublished: boolean = false;
  constructor(private scientificPaperService: ScientificPaperService, private xonomyApiService: XonomyApiService,
              private snackBar: MatSnackBar, private coverLettersService: CoverLetterService) {}

  ngOnInit() {
  }

  ngOnDestroy(): void {
    if(this.paperPublished == true && this.coverLetterPublished == false) {
      this.snackBar.open("All your data will be lost", 'Dismiss', {
        duration: 20000,
      });
      this.scientificPaperService.deleteScientificPaper(
        this.paperTitle
      ).subscribe(
        response => {
          this.paperPublished = false;
          this.snackBar.open(response, 'Dismiss', {
            duration: 3000,
          });
        }, error => {
          this.snackBar.open(error, 'Dismiss', {
            duration: 3000,
          });});
    }else{
      this.paperPublished = false;
      this.coverLetterPublished = false;
    }
  }

  readXMLFile(event) {
    const input = event.target;
    const fileReader = new FileReader();
    fileReader.onload = () => {
      this.newScientificPaperTemp = fileReader.result.toString();
      console.log(new DOMParser().parseFromString(fileReader.result.toString(), 'text/xml'));
      this.newScientificPaper = new DOMParser().parseFromString(fileReader.result.toString(), 'text/xml');
      this.renderXonomySP(this.separateMetadata(this.newScientificPaper));
    };
    fileReader.readAsText(input.files[0]);
  }
  readCoverLetterFile(event) {
    const input = event.target;
    const fileReader = new FileReader();
    fileReader.onload = () => {
      const fileContent = fileReader.result.toString();
      this.newCoverLetter = fileReader.result.toString();
      this.renderXonomyCL(fileContent);
    };
    fileReader.readAsText(input.files[0]);
  }

  separateMetadata(xmlDoc: Document) {
    const metadata = xmlDoc.getElementsByTagName('metadata')[0];
    this.metadata = xmlDoc.documentElement.removeChild(metadata);
    return xmlDoc;
  }

  getTitle(xmlDoc: Document){
    this.paperTitle = xmlDoc.getElementsByTagName('title')[0].textContent;
  }

 publishScientificPaper() {
   const newDocXml = new DOMParser().parseFromString(Xonomy.harvest(), 'text/xml');
   newDocXml.childNodes[0].insertBefore(this.metadata, newDocXml.childNodes[0].childNodes[0]); // vrati metadata
   this.scientificPaperService.saveScientificPaper(
       newDocXml.documentElement.outerHTML
     )
     .subscribe(
       response => {
         this.getTitle(newDocXml);
         this.paperPublished = true;
         this.newScientificPaperTemp = '';
         document.querySelector('div#SPdiv').innerHTML = "";
         this.snackBar.open(response, 'Dismiss', {
           duration: 3000,
         });
       },
       error => {
         this.snackBar.open(error, 'Dismiss', {
           duration: 3000,
         });
       },
       () => {
         console.log('Done!');
       }
     );
 }

  publishCoverLetter() {
    this.coverLettersService.saveCoverLetter(
      this.newCoverLetter, this.paperTitle.replace(/\s/g, "")
    ).subscribe(
        response => {
          this.coverLetterPublished = true;
          this.newCoverLetter = '';
          document.querySelector('div#CLdiv').innerHTML = "";
          this.snackBar.open(response, 'Dismiss', {
            duration: 3000,
          });
        },error => {
          this.snackBar.open(error, 'Dismiss', {
            duration: 3000,
          });
        });
  }

  renderXonomySP(xml: Document) {
    Xonomy.setMode('laic');
    Xonomy.render(xml, this.editorSP.nativeElement,
      {
        allowModeSwitching: true,
        elements: this.xonomyApiService.scientificPublicationSpecification.elements,
        onchange: () => { this.onArticleChange() }
      });
  }

  onArticleChange() {
      this.newScientificPaper = Xonomy.harvest();
    }
  onCoverLetterChange() {
    this.newCoverLetter = Xonomy.harvest();
  }

  onTabChanged($event) {
    switch ($event.index) {
      case (0):
        this.editorSP.nativeElement.innerHTML = '';
        break;
      case (1):
        this.editorCL.nativeElement.innerHTML = '';
        this.newScientificPaperTemp = '';
        break;
    }
  }

  renderXonomyCL(xml: string) {
    Xonomy.setMode('laic');
    Xonomy.render(xml, this.editorCL.nativeElement, {
      elements: this.xonomyApiService.scientificPublicationSpecification.elements,
      onchange: () => { this.onCoverLetterChange() }
    });
  }

}
