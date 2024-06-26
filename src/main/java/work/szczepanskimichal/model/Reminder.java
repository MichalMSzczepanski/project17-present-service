//package work.szczepanskimichal.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.UUID;
//
//@Entity
//@Table(name = "reminders")
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder(toBuilder = true)
//@Getter
//public class Reminder {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @Column(nullable = false)
//    private UUID owner;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(name = "reminder_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date reminderDate;
//
//    //todo recurring details
//        //recurring boolean for quick check
//        //recurring data structure with details
//}
