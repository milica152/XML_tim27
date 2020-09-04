export class selectedReviewer {
  email: string = "";
  selected: boolean = false;

  constructor(email: string, selected: boolean) {
    this.email = email;
    this.selected = selected;
  }

}
