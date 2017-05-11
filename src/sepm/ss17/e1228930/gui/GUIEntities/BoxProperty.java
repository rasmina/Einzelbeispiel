package sepm.ss17.e1228930.gui.GUIEntities;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sepm.ss17.e1228930.domain.Box;


public class BoxProperty {
    private static final Logger logger = LoggerFactory.getLogger(BoxProperty.class);

    private final IntegerProperty id;
    private final SimpleStringProperty horseName;
    private final DoubleProperty size;
    private final BooleanProperty window;
    private final SimpleStringProperty litter;
    private final DoubleProperty price;
    private final SimpleStringProperty location;
    private final SimpleStringProperty picture;

    public BoxProperty(BoxProperty bp){

        this.id= new SimpleIntegerProperty(bp.getId());
        this.horseName= new SimpleStringProperty(bp.getHorseName());
        this.size= new SimpleDoubleProperty(bp.getSize());
        this.window= new SimpleBooleanProperty(bp.isWindow());
        this.litter= new SimpleStringProperty(bp.getLitter());
        this.price= new SimpleDoubleProperty(bp.getPrice());
        this.location= new SimpleStringProperty(bp.getLocation());
        this.picture=new SimpleStringProperty(bp.getPicture());

        logger.debug("A new BoxProperty entity was created: {}", this);
    }
    /**
     * genereate a new BoxProperty entity from the parsed Box entity
     * @param b
     */
    public BoxProperty(Box b){

        this.id = new SimpleIntegerProperty(b.getBid());
        this.horseName = new SimpleStringProperty(b.getHorseName());
        this.size = new SimpleDoubleProperty(b.getSize());
        this.window = new SimpleBooleanProperty(b.isWindow());
        this.litter = new SimpleStringProperty(b.getLitter());
        this.price= new SimpleDoubleProperty(b.getPreisHour());
        this.location= new SimpleStringProperty(b.getLocation());
        this.picture= new SimpleStringProperty(b.getPicture());

        logger.debug("A new BoxProperty entity was created: {}", this);
    }

    //use this method to generate a Box entity out of an equivalent GUI Entity (BoxProperty)
    public static Box convertToBox(BoxProperty bp){
        logger.debug("A new BoxProperty entity was converted to a simple Box entity!");
        return new Box(false, bp.getId(), bp.isWindow() ,bp.getLitter(), bp.getHorseName(), bp.getSize() ,bp.getLocation(),bp.getPrice(),bp.getPicture());
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getHorseName() {
        return horseName.get();
    }

    public SimpleStringProperty horseNameProperty() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName.set(horseName);
    }

    public double getSize() {
        return size.get();
    }

    public DoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    public boolean isWindow() {
        return window.get();
    }

    public BooleanProperty windowProperty() {
        return window;
    }

    public void setWindow(boolean window) {
        this.window.set(window);
    }

    public String getLitter() {
        return litter.get();
    }

    public SimpleStringProperty litterProperty() {
        return litter;
    }

    public void setLitter(String litter) {
        this.litter.set(litter);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getPicture() {
        return picture.get();
    }

    public SimpleStringProperty pictureProperty() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture.set(picture);
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }


}
