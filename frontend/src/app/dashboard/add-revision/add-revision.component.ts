import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {ScientificPaperService} from '../../core/scientificPaper.service';
import {XonomyApiService} from "../../core/xonomy-api.service"
import {Router} from "@angular/router";
declare const Xonomy: any;

@Component({
  selector: 'app-add-revision',
  templateUrl: './add-revision.component.html',
  styleUrls: ['./add-revision.component.scss']
})
export class AddRevisionComponent implements OnInit {

  @ViewChild('editorRevision', { static: false }) editorRevision: ElementRef;
  paperTitle;
  newRevision: string = '';
  newRevisionTemp: Document;
  metadata: SVGMetadataElement;

  constructor(private snackBar: MatSnackBar, private activatedRoute: ActivatedRoute,
              private scientificPaperService: ScientificPaperService, private xonomyApiService: XonomyApiService,
              private router: Router) { }

  ngOnInit() {
    this.paperTitle = this.activatedRoute.snapshot.queryParams['paperTitle'];
  }

  async ngAfterViewInit() {
    this.scientificPaperService.getPaperXML(this.paperTitle).subscribe(
      response=>{
        this.newRevision = response;
        this.renderReview();
      },
      error => {
        this.snackBar.open(error.error.message, 'Dismiss', {
          duration: 3000,
        });
      }
    )
  }
  separateMetadata(xmlDoc: Document) {
    const metadata = xmlDoc.getElementsByTagName('metadata')[0];
    this.metadata = xmlDoc.documentElement.removeChild(metadata);
    return xmlDoc;
  }

  renderReview() {
    Xonomy.setMode('laic');
    this.newRevisionTemp = new DOMParser().parseFromString(this.newRevision, 'text/xml');
    Xonomy.render(this.separateMetadata(this.newRevisionTemp), this.editorRevision.nativeElement, {
      allowModeSwitching: true,
      elements: this.xonomyApiService.scientificPublicationSpecification.elements,
      onchange: () => { this.onReviewChange() }
    });
  }

  onReviewChange() {
    this.newRevisionTemp = Xonomy.harvest();
  }

  sendRevision() {
    const newDocXml = new DOMParser().parseFromString(Xonomy.harvest(), 'text/xml');
    newDocXml.childNodes[0].insertBefore(this.metadata, newDocXml.childNodes[0].childNodes[0]); // vrati metadata
    this.scientificPaperService.saveScientificPaper(
      newDocXml.documentElement.outerHTML,'true', this.paperTitle
    )
      .subscribe(
        response => {
          this.snackBar.open("Revision sent successfully!", 'Dismiss', {
            duration: 3000,
          });
          this.router.navigate(['dashboard/previewAllPapers'])
        },
        error => {
          this.snackBar.open(error, 'Dismiss', {
            duration: 3000,
          });
        }
      );
  }



}
