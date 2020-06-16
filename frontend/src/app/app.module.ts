import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing/app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FlexLayoutModule} from '@angular/flex-layout';
import {DashboardModule} from './dashboard/dashboard.module';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ToolbarModule} from './toolbar/toolbar.module';
import {AuthModule} from './auth/auth.module';
import {TokenInterceptor} from './interceptors/token.interceptor';
import {UrlInterceptor} from './interceptors/url.interceptor';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    DashboardModule,
    HttpClientModule,
    FlexLayoutModule,
    ToolbarModule,
    AuthModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
              {provide: HTTP_INTERCEPTORS, useClass: UrlInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
