import { Routes } from '@angular/router';
import { NdrConverterComponent } from '../components/ndr-converter.component';


export const ROUTES: Routes = [
    {
        path: '',
        data: {
            title: 'NDR Converter',
            breadcrumb: 'NDR CONVERTER'
        },
        children: [
            {
                path: '',
                component: NdrConverterComponent,
                data: {
                    breadcrumb: 'NDR CONVERTER'
                },
            }
        ]
    }
];

