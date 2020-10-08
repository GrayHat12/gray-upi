import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(GrayUPI)
public class GrayUPI: CAPPlugin {

    @objc func initialise(_ call: CAPPluginCall) {
        call.reject()
    }
    @objc func startPayment(_ call: CAPPluginCall) {
        call.reject()
    }
}
