import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Objects2 extends Objects {

    private LocalDateTime localDateTimeObject;
    private LocalTime localTimeObject;
    private LocalDate localDateObject;

    public Objects2(){}


    public LocalDateTime getLocalDateTimeObject() {
        return localDateTimeObject;
    }

    public void setLocalDateTimeObject(LocalDateTime localDateTimeObject) {
        this.localDateTimeObject = localDateTimeObject;
    }

    public LocalTime getLocalTimeObject() {
        return localTimeObject;
    }

    public void setLocalTimeObject(LocalTime localTimeObject) {
        this.localTimeObject = localTimeObject;
    }

    public LocalDate getLocalDateObject() {
        return localDateObject;
    }

    public void setLocalDateObject(LocalDate localDateObject) {
        this.localDateObject = localDateObject;
    }
}
