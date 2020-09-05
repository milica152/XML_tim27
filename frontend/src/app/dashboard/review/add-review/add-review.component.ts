import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar} from '@angular/material';
import {ActivatedRoute} from '@angular/router';
import {ReviewService} from '../../../core/review.service';
import {XonomyApiService} from "../../../core/xonomy-api.service"
import {Router} from "@angular/router";
declare const Xonomy: any;

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.scss']
})
export class AddReviewComponent implements OnInit {

  @ViewChild('editorReview', { static: false }) editorReview: ElementRef;
  newReview: Document;
  newReviewTemp: string = '';
  paperTitle;
  reviewTitle;

  constructor(private snackBar: MatSnackBar, private activatedRoute: ActivatedRoute,
              private reviewService: ReviewService, private xonomyApiService: XonomyApiService,
              private router: Router) { }

  ngOnInit() {
    this.paperTitle = this.activatedRoute.snapshot.queryParams['paperTitle'];
  }

  async ngAfterViewInit() {
    this.newReviewTemp = this.xonomyApiService.getReviewTemplate(this.paperTitle);
    this.renderReview();
  }

  renderReview() {
    Xonomy.setMode('laic');
    Xonomy.render(this.newReviewTemp, this.editorReview.nativeElement, {
      allowModeSwitching: true,
      elements: this.xonomyApiService.reviewSpecification.elements,
      onchange: () => { this.onReviewChange() }
    });
  }

  onReviewChange() {
    this.newReviewTemp = Xonomy.harvest();
  }

  addReview(){
    const newDocXml = new DOMParser().parseFromString(Xonomy.harvest(), 'text/xml');
    this.reviewService.saveReview(newDocXml.documentElement.outerHTML).subscribe(
      response => {
        if(response == 'error'){
          this.snackBar.open("Some of elements doesn't have right values (answer, paper rating or recommendation)",
            'Dismiss', {
            duration: 3000,
          });
        }else{
          this.newReviewTemp = '';
          document.querySelector('div#Reviewdiv').innerHTML = "";
          this.snackBar.open("Review sent successfully!", 'Dismiss', {
            duration: 3000,
          });
          this.router.navigate(['/dashboard/previewAllPapers']);
        }
      },error => {
        this.snackBar.open(error.error.message, 'Dismiss', {
          duration: 3000,
        });
      });
  }
}
