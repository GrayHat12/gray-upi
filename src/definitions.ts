declare module '@capacitor/core' {
  interface PluginRegistry {
    GrayUPI: GrayUPIPlugin;
  }
}

export interface TransactionDetails {
  transID: string;
  responseCode: string;
  approvalRefNo: string;
  transactionStatus: "SUBMITTED" | "SUCCESS" | "FAILURE";
  transactionRefId: string;
  amount: string;
};

export interface Transaction {
  vpa: string;
  name: string;
  transID: string;
  transRefID: string;
  description: string;
  amount: string;
};

export interface GrayUPIPlugin {
  initialise(transaction: Transaction): Promise<void>;
  startPayment(): Promise<void>;
  /*addListener(eventName: "onTransactionCompleted",
    listenerFunc: (transactionDetails: TransactionDetails) => void):
    PluginListenerHandle;
  addListener(eventName: "onTransactionCancelled",
    listenerFunc: () => void):
    PluginListenerHandle;
  addListener(
    eventName: string,
    listenerFunc: (...args: any[]) => any,
  ): PluginListenerHandle;*/
}