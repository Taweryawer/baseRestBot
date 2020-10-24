package taweryawer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Data
@NoArgsConstructor
public class OrderDTO {
    private static final DateTimeFormatter dateFormat
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Long id;

    private String ordererName;

    private String orderStatus;

    private String paymentMethod;

    private String dateTime;

    public void setSubmissionDate(LocalDateTime date) {
        dateFormat.withZone(TimeZone.getTimeZone("Europe/Kiev").toZoneId());
        this.dateTime = dateFormat.format(date);
    }
}
