package com.ryx.ryxcredit.ryd.bean.borrow.repayment;

import com.ryx.ryxcredit.beans.bussiness.CbaseResponse;
import com.ryx.ryxcredit.beans.bussiness.borrowdetail.BorrowRecordDetailResponse;

import java.util.List;

/**
 * Created by xiepp on 2017/6/1.
 */

public class CRYDRepayRecordResponse extends CbaseResponse {

    /**
     * result : {"repayments":[{"loan_status":"S","repayment_amount":529.01,"repayment_datetime":"20171117190226"},{"loan_status":"S","repayment_amount":100.01,"repayment_datetime":"20171117185953"}]}
     * code : 2000
     */

    private ResultBean result;
    private String code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultBean {
        private List<RepaymentsBean> repayments;

        public List<RepaymentsBean> getRepayments() {
            return repayments;
        }

        public void setRepayments(List<RepaymentsBean> repayments) {
            this.repayments = repayments;
        }

        public static class RepaymentsBean extends BorrowRecordDetailResponse.ResultBean.RepaymentsBean {
            /**
             * loan_status : S
             * repayment_amount : 529.01
             * repayment_datetime : 20171117190226
             */

            private String loan_status;
            private double repayment_amount;
            private String repayment_datetime;

            public String getLoan_status() {
                return loan_status;
            }

            public void setLoan_status(String loan_status) {
                this.loan_status = loan_status;
            }

            public double getRepayment_amount() {
                return repayment_amount;
            }

            public void setRepayment_amount(double repayment_amount) {
                this.repayment_amount = repayment_amount;
            }

            public String getRepayment_datetime() {
                return repayment_datetime;
            }

            public void setRepayment_datetime(String repayment_datetime) {
                this.repayment_datetime = repayment_datetime;
            }
        }
    }
}
