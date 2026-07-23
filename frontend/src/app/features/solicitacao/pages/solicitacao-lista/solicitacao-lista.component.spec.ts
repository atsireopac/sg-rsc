import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitacaoListaComponent } from './solicitacao-lista.component';

describe('SolicitacaoListaComponent', () => {
  let component: SolicitacaoListaComponent;
  let fixture: ComponentFixture<SolicitacaoListaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitacaoListaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SolicitacaoListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
