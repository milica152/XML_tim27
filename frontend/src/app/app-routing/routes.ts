import {Routes} from '@angular/router';
import {DashboardComponent} from '../dashboard/dashboard.component';
import {LoginComponent} from '../auth/components/login/login.component';
import {RegisterComponent} from '../auth/components/register/register.component';
import {PaperFullPreviewComponent} from '../dashboard/preview-my-papers/paper-full-preview/paper-full-preview.component'

export const routes: Routes = [
  {
    path: 'dashboard/:content',
    component: DashboardComponent
  },
  {
    path: 'dashboard',
    redirectTo: '/dashboard/previewAllPapers',
    pathMatch: 'full'
  },
  {
    path: '',
    redirectTo: '/dashboard/previewAllPapers',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'dashboard/:content/:paperName',
    component: DashboardComponent,
    pathMatch: 'full'
  }
];
