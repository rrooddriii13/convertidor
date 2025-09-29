export interface InspectionResult {
  format: 'SCT' | 'SDD';
  totalAmount: number;
  currency: string;
  numberOfTransactions: number;
  creditorName?: string;
  debtorName?: string;
  executionDate: string;
  messageId: string;
  warnings: string[];
  isValid: boolean;
  schema: string;
  creationDateTime: string;
  initiatingParty?: string;
}