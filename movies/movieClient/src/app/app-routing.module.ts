import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RevenuePlanComponent } from './tests/components/revenue-plan/revenue-plan.component';

const routes: Routes = [
  { path: '', component: RevenuePlanComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
