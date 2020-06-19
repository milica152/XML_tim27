export class PaperPreviewModel {
  title: string;
  status: string;
  enabled: boolean;

  constructor(title: string, status: string, enabled:boolean) {
    this.title = title;
    this.status = status;
    this.enabled = enabled;
  }
}
