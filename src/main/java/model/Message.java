package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Message {
    private long id;
    private User fromId;
    private User toId;
    private String message;
    private Date date;
    private String file;

}
