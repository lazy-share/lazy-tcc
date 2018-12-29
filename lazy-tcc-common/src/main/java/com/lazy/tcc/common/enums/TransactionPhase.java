package com.lazy.tcc.common.enums;

/**
 * <p>
 * TransactionPhase Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/14.
 */
public enum TransactionPhase {


    TRY(1, "try"),
    CONFIRM(2, "confirm"),
    CANCEL(3, "cancel"),

    ;

    private int val;
    private String desc;

    TransactionPhase(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public TransactionPhase setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getVal() {
        return val;
    }

    public TransactionPhase setVal(int val) {
        this.val = val;
        return this;
    }

    public static TransactionPhase valueOf(int val) {

        switch (val) {
            case 1:
                return TRY;
            case 2:
                return CONFIRM;
            default:
                return CANCEL;
        }
    }

}
