import { InspectionResult } from "./InspectionResult";


export interface ConversionResult {
  originalFormat: 'SCT' | 'SDD';
  targetFormat: 'SCT' | 'SDD';
  originalData: InspectionResult;
  convertedData: InspectionResult;
  xmlContent: string;
  conversionLog: string[];
  success: boolean;
  timestamp: string;
}