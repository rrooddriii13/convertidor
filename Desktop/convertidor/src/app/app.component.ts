// import { Component } from '@angular/core';
// import { RouterOutlet } from '@angular/router';

// @Component({
//   selector: 'app-root',
//   imports: [RouterOutlet],
//   templateUrl: './app.component.html',
//   styleUrl: './app.component.css'
// })
// export class AppComponent {
//   title = 'convertidor';
// }


import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';

import { HeaderComponent } from './componentes/HeaderComponent/header.component';
import { FileUploadComponent } from './componentes/FileUploadComponent/file-upload.component';
import { ConversionService } from './servicios/ConversionService';
import { InspectionResult } from './modelos/interfaces/InspectionResult';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    CommonModule,
    HeaderComponent,
    FileUploadComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Convertidor SEPA SCT ↔ SDD';
  
  selectedFile: File | null = null;
  inspectionResult: InspectionResult | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(private conversionService: ConversionService) {}

  onFileSelected(file: File) {
    this.selectedFile = file;
    this.inspectionResult = null;
    this.error = null;
    this.inspectFile();
  }

  private inspectFile() {
    if (!this.selectedFile) return;
    
    this.isLoading = true;
    this.conversionService.inspectFile(this.selectedFile).subscribe({
      next: (result) => {
        this.inspectionResult = result;
        this.isLoading = false;
      },
      error: (error) => {
        this.error = 'Error al inspeccionar el archivo: ' + error.message;
        this.isLoading = false;
      }
    });
  }

  onReset() {
    this.selectedFile = null;
    this.inspectionResult = null;
    this.error = null;
  }
}