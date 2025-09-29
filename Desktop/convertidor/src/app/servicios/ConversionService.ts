import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, delay } from 'rxjs';
import { InspectionResult } from '../modelos/interfaces/InspectionResult';
import { ConversionResult } from '../modelos/interfaces/ConversionResultado';
import { ValidationResult } from '../modelos/interfaces/ValidationResult';

@Injectable({
  providedIn: 'root'
})
export class ConversionService {
  private readonly baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  inspectFile(file: File): Observable<InspectionResult> {
    // Simulación temporal hasta tener el backend
    const mockResult: InspectionResult = {
      format: file.name.includes('sct') ? 'SCT' : 'SDD',
      totalAmount: 15750.50,
      currency: 'EUR',
      numberOfTransactions: 25,
      creditorName: 'EMPRESA DEMO S.L.',
      debtorName: 'BANCO EJEMPLO',
      executionDate: '2024-12-20',
      messageId: 'MSG-' + Date.now(),
      warnings: [
        'Algunos campos opcionales están vacíos',
        'Se recomienda verificar los códigos BIC'
      ],
      isValid: true,
      schema: file.name.includes('sct') ? 'pain.001.001.09' : 'pain.008.001.08',
      creationDateTime: new Date().toISOString(),
      initiatingParty: 'EMPRESA DEMO S.L.'
    };

    return of(mockResult).pipe(delay(1500)); // Simula tiempo de procesamiento

    // Implementación real cuando esté el backend:
    // const formData = new FormData();
    // formData.append('file', file);
    // return this.http.post<InspectionResult>(`${this.baseUrl}/inspect`, formData);
  }

  convertFile(file: File): Observable<ConversionResult> {
    // Simulación temporal
    const originalData: InspectionResult = {
      format: 'SCT',
      totalAmount: 15750.50,
      currency: 'EUR',
      numberOfTransactions: 25,
      creditorName: 'EMPRESA DEMO S.L.',
      debtorName: 'BANCO EJEMPLO',
      executionDate: '2024-12-20',
      messageId: 'MSG-ORIGINAL-' + Date.now(),
      warnings: [],
      isValid: true,
      schema: 'pain.001.001.09',
      creationDateTime: new Date().toISOString()
    };

    const convertedData: InspectionResult = {
      ...originalData,
      format: 'SDD',
      messageId: 'MSG-CONVERTED-' + Date.now(),
      schema: 'pain.008.001.08'
    };

    const mockResult: ConversionResult = {
      originalFormat: 'SCT',
      targetFormat: 'SDD',
      originalData,
      convertedData,
      xmlContent: this.generateMockXML('SDD'),
      conversionLog: [
        'Validando archivo original...',
        'Extrayendo datos de transferencias...',
        'Mapeando campos SCT a SDD...',
        'Generando nueva estructura XML...',
        'Validando archivo convertido...',
        'Conversión completada exitosamente'
      ],
      success: true,
      timestamp: new Date().toISOString()
    };

    return of(mockResult).pipe(delay(2000));

    // Implementación real:
    // const formData = new FormData();
    // formData.append('file', file);
    // return this.http.post<ConversionResult>(`${this.baseUrl}/convert`, formData);
  }

  validateFile(file: File): Observable<ValidationResult> {
    const mockResult: ValidationResult = {
      isValid: true,
      errors: [],
      warnings: ['Archivo válido pero se encontraron campos opcionales vacíos']
    };

    return of(mockResult).pipe(delay(1000));
  }

  downloadConvertedFile(xmlContent: string, filename: string): void {
    const blob = new Blob([xmlContent], { type: 'application/xml' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  }

  private generateMockXML(format: 'SCT' | 'SDD'): string {
    const timestamp = new Date().toISOString();
    const messageId = `MSG-${format}-${Date.now()}`;
    
    if (format === 'SDD') {
      return `<?xml version="1.0" encoding="UTF-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08">
  <CstmrDrctDbtInitn>
    <GrpHdr>
      <MsgId>${messageId}</MsgId>
      <CreDtTm>${timestamp}</CreDtTm>
      <NbOfTxs>25</NbOfTxs>
      <CtrlSum>15750.50</CtrlSum>
    </GrpHdr>
    <!-- Resto del XML SDD simulado -->
  </CstmrDrctDbtInitn>
</Document>`;
    } else {
      return `<?xml version="1.0" encoding="UTF-8"?>
<Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.001.001.09">
  <CstmrCdtTrfInitn>
    <GrpHdr>
      <MsgId>${messageId}</MsgId>
      <CreDtTm>${timestamp}</CreDtTm>
      <NbOfTxs>25</NbOfTxs>
      <CtrlSum>15750.50</CtrlSum>
    </GrpHdr>
    <!-- Resto del XML SCT simulado -->
  </CstmrCdtTrfInitn>
</Document>`;
    }
  }
}