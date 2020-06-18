import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewMyPapersComponent } from './preview-my-papers.component';

describe('PreviewMyPapersComponent', () => {
  let component: PreviewMyPapersComponent;
  let fixture: ComponentFixture<PreviewMyPapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewMyPapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewMyPapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
