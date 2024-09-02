package work.szczepanskimichal.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.notification.Notification;
import work.szczepanskimichal.model.notification.NotificationSubject;
import work.szczepanskimichal.model.notification.NotificationType;
import work.szczepanskimichal.model.person.Person;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaService kafkaService;

    public void sendReminderMessage(String userEmail, Person person) {
        var parameters = new HashMap<String, String>();
        parameters.put("person", String.valueOf(person));
        var notification = Notification.builder()
                .addressee(userEmail)
                .type(NotificationType.EMAIL)
                .subject(NotificationSubject.REMINDER_TRIGGERING)
                .messageParameters(parameters)
                .build();
        kafkaService.sendMessage(notification);
    }


}
