import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaTipos } from './lista-tipos';

describe('ListaTipos', () => {
  let component: ListaTipos;
  let fixture: ComponentFixture<ListaTipos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaTipos],
    }).compileComponents();

    fixture = TestBed.createComponent(ListaTipos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
