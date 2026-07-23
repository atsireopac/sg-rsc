import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentoListaPageComponent } from './documento-lista-page.component';

describe('DocumentoListaPageComponent', () => {
  let component: DocumentoListaPageComponent;
  let fixture: ComponentFixture<DocumentoListaPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DocumentoListaPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DocumentoListaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
