import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";
import { NewScientificPaper } from "src/app/shared/models/newScientificPaper.model";
declare const Xonomy: any;

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { position: 1, name: "Hydrogen", weight: 1.0079, symbol: "H" },
  { position: 2, name: "Helium", weight: 4.0026, symbol: "He" },
  { position: 3, name: "Lithium", weight: 6.941, symbol: "Li" },
  { position: 4, name: "Beryllium", weight: 9.0122, symbol: "Be" },
  { position: 5, name: "Boron", weight: 10.811, symbol: "B" },
  { position: 6, name: "Carbon", weight: 12.0107, symbol: "C" },
  { position: 7, name: "Nitrogen", weight: 14.0067, symbol: "N" },
  { position: 8, name: "Oxygen", weight: 15.9994, symbol: "O" },
  { position: 9, name: "Fluorine", weight: 18.9984, symbol: "F" },
  { position: 10, name: "Neon", weight: 20.1797, symbol: "Ne" }
];

@Component({
  selector: "app-author-dashboard",
  templateUrl: "./author-dashboard.component.html",
  styleUrls: ["./author-dashboard.component.scss"]
})
export class AuthorDashboardComponent implements OnInit {
  @ViewChild("editor", { static: false }) editor: ElementRef;
  //@ViewChild("spTitle", { static: false }) spTitle: ElementRef;
  displayedColumns: string[] = ["position", "name", "weight", "symbol"];
  dataSource = ELEMENT_DATA;
  newScientificPaper: NewScientificPaper = new NewScientificPaper();
 // constructor(private scientificPaperService: ScientificPaperService) {}

  ngOnInit() {}

  start() {
    console.log(this.editor.nativeElement);
  }

  readXMLFile(event) {
    let input = event.target;
    let fileReader = new FileReader();
    fileReader.onload = () => {
      let fileContent = fileReader.result.toString();
      this.newScientificPaper.content = fileReader.result.toString();
      this.renderXonomy(fileContent);
    };
    fileReader.readAsText(input.files[0]);
  }
 /*
  publishScientificPaper() {
    console.log(this.newScientificPaper);
    this.scientificPaperService
      .saveScientificPaper(
        this.newScientificPaper.title,
        this.newScientificPaper.content
      )
      .subscribe(
        response => {
          console.log(response);
        },
        error => {
          console.log(error);
        },
        () => {
          console.log("Done!");
        }
      );
  } */

  renderXonomy(xml: string) {
    Xonomy.setMode("laic");
    Xonomy.render(xml, this.editor.nativeElement, null);
  }
}
