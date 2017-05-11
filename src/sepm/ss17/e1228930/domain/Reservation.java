package sepm.ss17.e1228930.domain;

import java.security.Timestamp;
import java.sql.Time;


public class Reservation {
    private boolean isDeleted;
    private int r_id;
    private String clientName;
    private String horseName;
    private java.sql.Timestamp start;
    private java.sql.Timestamp ende;

    public Reservation(int r_id, String clientName, String horseName, java.sql.Timestamp start, java.sql.Timestamp ende) {
        this.r_id = r_id;
        this.clientName = clientName;
        this.horseName = horseName;
        this.start = start;
        this.ende = ende;
    }

    public Reservation(int r_id, String clientName, String horseName, java.sql.Timestamp start, java.sql.Timestamp ende,boolean isDeleted) {
        this.isDeleted = isDeleted;
        this.r_id = r_id;
        this.clientName = clientName;
        this.horseName = horseName;
        this.start = start;
        this.ende = ende;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public java.sql.Timestamp getStart() {
        return start;
    }

    public void setStart(java.sql.Timestamp start) {
        this.start = start;
    }

    public java.sql.Timestamp getende() {
        return ende;
    }

    public void setende(java.sql.Timestamp ende) {
        this.ende = ende;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "isDeleted=" + isDeleted +
                ", r_id=" + r_id +
                ", clientName='" + clientName + '\'' +
                ", horseName='" + horseName + '\'' +
                ", start=" + start +
                ", ende=" + ende +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (isDeleted != that.isDeleted) return false;
        if (r_id != that.r_id) return false;
        if (clientName != null ? !clientName.equals(that.clientName) : that.clientName != null) return false;
        if (horseName != null ? !horseName.equals(that.horseName) : that.horseName != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        return ende != null ? ende.equals(that.ende) : that.ende == null;
    }

    @Override
    public int hashCode() {
        int result = (isDeleted ? 1 : 0);
        result = 31 * result + r_id;
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (horseName != null ? horseName.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (ende != null ? ende.hashCode() : 0);
        return result;
    }
}
