package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDate;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreatedDto;
import work.szczepanskimichal.repository.ReminderDateRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderDateService {

    private final ReminderDateRepository reminderDateRepository;
    private final ReminderService reminderService;
    private final ReminderDateMapper reminderMapper;

    public ReminderDateCreatedDto createReminder(ReminderDateCreateDto reminderCreateDto) {
        var parentReminder = reminderService.getReminderById(reminderCreateDto.getReminderId());
        var reminder = reminderMapper.toEntity(reminderCreateDto)
                .toBuilder()
                .reminder(parentReminder)
                .build();
        return reminderMapper.toDto(reminderDateRepository.save(reminder));
    }

    public ReminderDate getReminderDateById(UUID id) {
        return reminderDateRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<ReminderDate> getReminderDatesByReminder(UUID reminderId) {
        return reminderDateRepository.getReminderDatesByReminderId(reminderId);
    }
}
