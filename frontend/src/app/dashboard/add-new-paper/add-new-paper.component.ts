import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ScientificPaperService} from '../../core/scientificPaper.service';
import {XonomyApiService} from '../../core/xonomy-api.service';
import {CoverLetterService} from '../../core/cover-letter.service';

declare const Xonomy: any;

@Component({
  selector: 'app-add-new-paper',
  templateUrl: './add-new-paper.component.html',
  styleUrls: ['./add-new-paper.component.scss']
})
export class AddNewPaperComponent implements OnInit {
  @ViewChild('editorSP', { static: false }) editorSP: ElementRef;
  @ViewChild('editorCL', { static: false }) editorCL: ElementRef;
  // @ViewChild("spTitle", { static: false }) spTitle: ElementRef;
  newScientificPaperTemp: string;
  newScientificPaper: Document;
  newCoverLetter = '';
  metadata: SVGMetadataElement;
  constructor(private scientificPaperService: ScientificPaperService,
              private coverLetterService: CoverLetterService,
              private xonomyApiService: XonomyApiService) {}

  ngOnInit() {}

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
      this.newCoverLetter = fileReader.result.toString();
    };
    fileReader.readAsText(input.files[0]);
  }
  separateMetadata(xmlDoc: Document) {
    const metadata = xmlDoc.getElementsByTagName('metadata')[0];
    this.metadata = xmlDoc.documentElement.removeChild(metadata);
    return xmlDoc;
  }

 publishScientificPaper() {
    const newDocXml = new DOMParser().parseFromString(Xonomy.harvest(), 'text/xml');
    newDocXml.childNodes[0].insertBefore(this.metadata, newDocXml.childNodes[0].childNodes[0]); // vrati metadata
    const newDocString = this.removeXMLSpace(newDocXml.documentElement.outerHTML);
    this.scientificPaperService.saveScientificPaper(newDocString)
     .subscribe(
       response => {
         console.log(response);
       },
       error => {
         console.log(error);
       },
       () => {
         this.coverLetterService.saveCoverLetter(this.removeXMLSpace(this.newCoverLetter)).subscribe(
           response => {
             console.log(response);
           },
           error => {
             console.log(error);
           },
           () => {
             console.log('Done!');
           }
         );
       }
     );
 }



  removeXMLSpace(str: string) {
    return str.replace(/xml:space="preserve"/g, '');
  }


  renderXonomySP(xml: Document) {
    Xonomy.setMode('laic');
    Xonomy.render(xml, this.editorSP.nativeElement, this.xonomyApiService.scientificPublicationSpecification);
  }

  renderXonomyCL(xml: string) {
    Xonomy.setMode('laic');
    Xonomy.render(xml, this.editorCL.nativeElement, this.xonomyApiService.coverLetterSpecification);
  }
}
