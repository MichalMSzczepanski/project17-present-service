package work.szczepanskimichal.service;

import work.szczepanskimichal.model.Reminder;

abstract class ReminderAssembler {

    static Reminder assembleReminder(String reminderName) {
        return Reminder.builder()
                .name(reminderName)
                .build();
    }

}
