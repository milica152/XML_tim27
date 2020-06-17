import { Component, OnInit } from '@angular/core';
import { ScientificPaperService } from 'src/app/services/scientificPaper.service';

@Component({
  selector: 'app-preview-my-papers',
  templateUrl: './preview-my-papers.component.html',
  styleUrls: ['./preview-my-papers.component.scss']
})
export class PreviewMyPapersComponent implements OnInit {

  message: string = 'ovo je ok';

  constructor(private scientificPaperService: ScientificPaperService) { }

  ngOnInit() {
    this.scientificPaperService.getMyPapers().subscribe(
      response => {
        console.log(response);
      });
  }

  testClick() {
    this.scientificPaperService.getMyPapers().subscribe(
      response => {
        console.log(response);
      });
  }

}
