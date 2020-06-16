import {Component, OnInit, ViewChild, AfterViewInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {PaginatorComponent} from './paginator/paginator.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements AfterViewInit, OnInit {
  @ViewChild(PaginatorComponent, {static: false}) paginator: PaginatorComponent;
  private _content: string;

  constructor(private route: ActivatedRoute) {
  }

  get content(): string {
    return this._content;
  }

  set content(value: string) {
    this._content = value;
  }

  ngAfterViewInit() {
  }

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this._content = params.get('content');
    });
  }
}
