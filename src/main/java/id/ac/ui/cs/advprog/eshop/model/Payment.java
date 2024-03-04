package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String,String> paymentData) {
        if (method.equals("voucherCode")) {
            this.payWithVoucher(paymentData);
        } else if (method.equals("bankTransfer")) {
            this.payWithBankTransfer(paymentData);
        } else {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void payWithVoucher(Map<String, String> paymentData) {
        if (!paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException();
        }
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null) {
            this.status = PaymentStatus.REJECTED.getValue();
            return;
        }

        if (!(voucherCode.length() == 16)) {
            this.status = PaymentStatus.REJECTED.getValue();
        } else if (!voucherCode.startsWith("ESHOP")) {
            this.status = PaymentStatus.REJECTED.getValue();
        } else {
            int digitCount = 0;
            for (int i = 0; i < voucherCode.length(); i++) {
                char ch = voucherCode.charAt(i);
                if (Character.isDigit(ch)) {
                    digitCount++;
                }
            }
            
            if (digitCount != 8) {
                this.status = PaymentStatus.REJECTED.getValue();
            } else {
                this.status = PaymentStatus.SUCCESS.getValue();
            }
        }
    }

    private void payWithBankTransfer(Map<String, String> paymentData) {
        if (!paymentData.containsKey("bankName") || !paymentData.containsKey("referenceCode")) {
            throw new IllegalArgumentException();
        }
        String bank = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bank == null|| bank.isEmpty() || referenceCode == null || referenceCode.isEmpty()){
            this.status = PaymentStatus.REJECTED.getValue();
        } else {
            this.status = PaymentStatus.SUCCESS.getValue();
        }
    }
}
