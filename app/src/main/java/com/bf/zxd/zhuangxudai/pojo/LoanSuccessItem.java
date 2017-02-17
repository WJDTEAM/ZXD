package com.bf.zxd.zhuangxudai.pojo;

/**
 * Created by Daniel on 2017/2/17.
 */
public class LoanSuccessItem {

    /**
     * fullName : 哈先生
     * loanAmount : 6
     * phone : 156****2651
     */

    private String fullName;
    private int loanAmount;
    private String phone;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "LoanSuccessItem{" +
                "fullName='" + fullName + '\'' +
                ", loanAmount=" + loanAmount +
                ", phone='" + phone + '\'' +
                '}';
    }
}
