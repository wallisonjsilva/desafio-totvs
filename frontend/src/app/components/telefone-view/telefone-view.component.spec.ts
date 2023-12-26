import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TelefoneViewComponent } from './telefone-view.component';

describe('TelefoneViewComponent', () => {
  let component: TelefoneViewComponent;
  let fixture: ComponentFixture<TelefoneViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TelefoneViewComponent]
    });
    fixture = TestBed.createComponent(TelefoneViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
