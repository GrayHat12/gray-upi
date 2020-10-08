package com.grayhat.upi;

import android.app.Activity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.shreyaspatil.easyupipayment.EasyUpiPayment;
import com.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import com.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import com.shreyaspatil.easyupipayment.model.TransactionDetails;

@NativePlugin
public class GrayUPI extends Plugin {

    EasyUpiPayment.Builder builder;
    EasyUpiPayment easyUpiPayment = null;
    String TAG = "EUPGUPI";

    @PluginMethod
    public void initialise(PluginCall call) {
        try {
            if (easyUpiPayment != null) {
                easyUpiPayment.removePaymentStatusListener();
            }
            String vpa = call.getString("vpa");
            String name = call.getString("name");
            String transID = call.getString("transID");
            String transRefID = call.getString("transRefID");
            String description = call.getString("description");
            String amount = call.getString("amount");
            builder = new EasyUpiPayment.Builder((Activity)getContext())
                    .setPayeeVpa(vpa)
                    .setPayeeName(name)
                    .setTransactionId(transID)
                    .setTransactionRefId(transRefID)
                    .setDescription(description)
                    .setAmount(amount);
            easyUpiPayment = builder.build();
            easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
                @Override
                public void onTransactionCompleted(TransactionDetails transactionDetails) {
                    JSObject object = new JSObject();
                    object.put("transID",transactionDetails.getTransactionId());
                    object.put("responseCode",transactionDetails.getResponseCode());
                    object.put("approvalRefNo",transactionDetails.getApprovalRefNo());
                    object.put("transactionStatus",transactionDetails.getTransactionStatus().name());//SUBMITTED/SUCCESS/FAILURE
                    object.put("transactionRefId",transactionDetails.getTransactionRefId());
                    object.put("amount",transactionDetails.getAmount());
                    notifyListeners("onTransactionCompleted",object);
                    //bridge.triggerWindowJSEvent("onTransactionCompleted",object.toString());
                }

                @Override
                public void onTransactionCancelled() {
                    //bridge.triggerWindowJSEvent("onTransactionCancelled");
                    notifyListeners("onTransactionCancelled",new JSObject());
                }
            });
            call.resolve();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.debug(TAG,e.getMessage());
            call.reject(e.getLocalizedMessage(),e);
        }
    }
    @PluginMethod
    public void startPayment(PluginCall call) {
        try {
            if(easyUpiPayment == null) {
                call.reject("Plugin not initialised");
            }else{
                easyUpiPayment.startPayment();
                call.resolve();
            }
        }catch(Exception ex) {
            call.reject(ex.getLocalizedMessage(),ex);
        }
    }
}
