import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import {
    MatButtonModule,
    MatCardModule,
    MatCheckboxModule,
    MatDividerModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatProgressBarModule,
    MatSelectModule,
    MatTabsModule
} from '@angular/material';
import { RouterModule } from '@angular/router';
import { NdrConverterComponent } from './components/ndr-converter.component';
import { NdrConverterService } from "./services/ndr-converter.service";
import { ROUTES } from "./services/ndr.route";
import { FormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        NdrConverterComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        MatInputModule,
        MatIconModule,
        MatDividerModule,
        MatCardModule,
        MatSelectModule,
        MatButtonModule,
        MatTabsModule,
        RouterModule.forChild(ROUTES),
        MatProgressBarModule,
        MatListModule,
        MatCheckboxModule
    ],
    exports: [
        NdrConverterComponent
    ],
    providers: [
        NdrConverterService
    ]
})
export class NdrModule {
}
