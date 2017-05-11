package sepm.ss17.e1228930.domain;

import java.sql.Timestamp;


public class Receipt {
    private int r_id;
    private int bid;
    private Integer receipt_id;
    private double totalPreis;
    private String clientName;
    private boolean isDeleted;
    private Timestamp start;

    public Receipt(int r_id, int bid, Integer receipt_id, double totalPreis,String clientName, Timestamp start) {
        this.r_id = r_id;
        this.bid = bid;
        this.receipt_id = receipt_id;
        this.totalPreis = totalPreis;
        this.clientName=clientName;
        this.start = start;
    }

    public Receipt(int r_id, int bid, Integer receipt_id, double totalPreis, String clientName, boolean isDeleted, Timestamp start) {
        this.r_id = r_id;
        this.bid = bid;
        this.receipt_id = receipt_id;
        this.totalPreis = totalPreis;
        this.clientName = clientName;
        this.isDeleted = isDeleted;
        this.start = start;
    }


    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Integer getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(Integer receipt_id) {
        this.receipt_id = receipt_id;
    }

    public double getTotalPreis() {
        return totalPreis;
    }

    public void setTotalPreis(double totalPreis) {
        this.totalPreis = totalPreis;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "r_id=" + r_id +
                ", bid=" + bid +
                ", receipt_id=" + receipt_id +
                ", totalPreis=" + totalPreis +
                ", clientName='" + clientName + '\'' +
                ", isDeleted=" + isDeleted +
                ", start=" + start +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (r_id != receipt.r_id) return false;
        if (bid != receipt.bid) return false;
        if (Double.compare(receipt.totalPreis, totalPreis) != 0) return false;
        if (isDeleted != receipt.isDeleted) return false;
        if (receipt_id != null ? !receipt_id.equals(receipt.receipt_id) : receipt.receipt_id != null) return false;
        if (clientName != null ? !clientName.equals(receipt.clientName) : receipt.clientName != null) return false;
        return start != null ? start.equals(receipt.start) : receipt.start == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = r_id;
        result = 31 * result + bid;
        result = 31 * result + (receipt_id != null ? receipt_id.hashCode() : 0);
        temp = Double.doubleToLongBits(totalPreis);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        return result;
    }
}
