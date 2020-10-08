import { WebPlugin } from '@capacitor/core';
import { GrayUPIPlugin, Transaction } from './definitions';

export class GrayUPIWeb extends WebPlugin implements GrayUPIPlugin {
  constructor() {
    super({
      name: 'GrayUPI',
      platforms: ['web'],
    });
  }
  initialise(transaction: Transaction): Promise<void> {
    throw new Error('Method not implemented.' + transaction.transID);
  }
  startPayment(): Promise<void> {
    throw new Error('Method not implemented.');
  }
}

const GrayUPI = new GrayUPIWeb();

export { GrayUPI };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(GrayUPI);
