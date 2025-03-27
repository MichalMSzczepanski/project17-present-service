package work.szczepanskimichal.service.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.notification.Notification;
import work.szczepanskimichal.model.notification.NotificationSubject;
import work.szczepanskimichal.model.notification.NotificationType;
import work.szczepanskimichal.model.person.Person;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final KafkaService kafkaService;

    public void sendReminderMessage(String userEmail, Person person) {
        var parameters = new HashMap<String, String>();
        parameters.put("person", person.getName() + " " + person.getLastname());
        //todo findFirst? will that be the correct occasion?
        parameters.put("occasion", person.getOccasions().stream().findFirst().get().getName());
        parameters.put("date", person.getOccasions().stream().findFirst().get().getDate().toString());
        var notification = Notification.builder()
                .addressee(userEmail)
                .type(NotificationType.EMAIL)
                .subject(NotificationSubject.REMINDER_TRIGGERING)
                .messageParameters(parameters)
                .build();
        kafkaService.sendMessage(notification);
        log.info("dispatched notification to {}. subject: {}", userEmail, NotificationSubject.REMINDER_TRIGGERING);
    }


}
