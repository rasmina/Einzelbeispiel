package sepm.ss17.e1228930.domain;


public class Box {

    private boolean isDeleted;
    private int bid;
    private boolean window;
    private String litter;
    private String horseName;
    private double size;
    private String location;
    private double preisHour;
    private String picture;

    public Box(){};

    public Box(boolean isDeleted, int bid, boolean window, String litter, String horseName, double size, String location, double preisHour, String picture) {
        this.isDeleted = isDeleted;
        this.bid = bid;
        this.window = window;
        this.litter = litter;
        this.horseName = horseName;
        this.size = size;
        this.location = location;
        this.preisHour = preisHour;
        this.picture = picture;
    }

    public Box(int bid, boolean window, String litter, String horseName, double size, String location, double preisHour, String picture) {
        this.bid = bid;
        this.window = window;
        this.litter = litter;
        this.horseName = horseName;
        this.size = size;
        this.location = location;
        this.preisHour = preisHour;
        this.picture = picture;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public boolean isWindow() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window = window;
    }

    public String getLitter() {
        return litter;
    }

    public void setLitter(String litter) {
        this.litter = litter;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPreisHour() {
        return preisHour;
    }

    public void setPreisHour(double preisHour) {
        this.preisHour = preisHour;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Box{" +
                "isDeleted=" + isDeleted +
                ", bid=" + bid +
                ", window=" + window +
                ", litter='" + litter + '\'' +
                ", horseName='" + horseName + '\'' +
                ", size=" + size +
                ", location='" + location + '\'' +
                ", preisHour=" + preisHour +
                ", picture='" + picture + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        if (isDeleted != box.isDeleted) return false;
        if (bid != box.bid) return false;
        if (window != box.window) return false;
        if (Double.compare(box.size, size) != 0) return false;
        if (Double.compare(box.preisHour, preisHour) != 0) return false;
        if (litter != null ? !litter.equals(box.litter) : box.litter != null) return false;
        if (horseName != null ? !horseName.equals(box.horseName) : box.horseName != null) return false;
        if (location != null ? !location.equals(box.location) : box.location != null) return false;
        return picture != null ? picture.equals(box.picture) : box.picture == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (isDeleted ? 1 : 0);
        result = 31 * result + bid;
        result = 31 * result + (window ? 1 : 0);
        result = 31 * result + (litter != null ? litter.hashCode() : 0);
        result = 31 * result + (horseName != null ? horseName.hashCode() : 0);
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        temp = Double.doubleToLongBits(preisHour);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        return result;
    }
}
