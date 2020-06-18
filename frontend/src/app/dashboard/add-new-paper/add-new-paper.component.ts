import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NewScientificPaper} from '../../shared/models/newScientificPaper.model';
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
  newScientificPaper: NewScientificPaper = new NewScientificPaper();
  newCoverLetter = '';
  constructor(private scientificPaperService: ScientificPaperService,
              private coverLetterService: CoverLetterService,
              private xonomyApiService: XonomyApiService) {}

  ngOnInit() {}

  readXMLFile(event) {
    const input = event.target;
    const fileReader = new FileReader();
    fileReader.onload = () => {
      const fileContent = fileReader.result.toString();
      this.newScientificPaper.content = fileReader.result.toString();
      this.renderXonomySP(fileContent);
    };
    fileReader.readAsText(input.files[0]);
  }
  readCoverLetterFile(event) {
    const input = event.target;
    const fileReader = new FileReader();
    fileReader.onload = () => {
      const fileContent = fileReader.result.toString();
      this.newCoverLetter = fileContent;
    };
    fileReader.readAsText(input.files[0]);
  }

 publishScientificPaper() {
   this.scientificPaperService.saveScientificPaper(
       Xonomy.harvest()
     )
     .subscribe(
       response => {
         console.log(response);
       },
       error => {
         console.log(error);
       },
       () => {
         if (this.newCoverLetter !== '') {
           this.coverLetterService.saveCoverLetter(this.newCoverLetter);
         }
         console.log('Done!');
       }
     );
 }

  renderXonomySP(xml: string) {
    Xonomy.setMode('laic');
    Xonomy.render(xml, this.editorSP.nativeElement, this.xonomyApiService.scientificPublicationSpecification);
  }

  renderXonomyCL(xml: string) {
    Xonomy.setMode('laic');
    Xonomy.render(xml, this.editorCL.nativeElement, this.xonomyApiService.coverLetterSpecification);
  }
}
