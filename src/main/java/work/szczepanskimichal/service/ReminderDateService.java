package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateUpdateDto;
import work.szczepanskimichal.repository.ReminderDateRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderDateService {

    private final ReminderDateRepository reminderDateRepository;
    private final ReminderService reminderService;
    private final ReminderDateMapper reminderMapper;

    public ReminderDateDto createReminder(ReminderDateCreateDto reminderCreateDto) {
        var parentReminder = reminderService.getReminderById(reminderCreateDto.getReminderId());
        var reminder = reminderMapper.toEntity(reminderCreateDto)
                .toBuilder()
                .reminder(parentReminder)
                .build();
        return reminderMapper.toDto(reminderDateRepository.save(reminder));
    }

    public ReminderDateDto getReminderDateById(UUID id) {
        return reminderMapper.toDto(reminderDateRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public List<ReminderDateDto> getReminderDatesByReminder(UUID reminderId) {
        var reminders = reminderDateRepository.getReminderDatesByReminderId(reminderId);
        return reminders.stream()
                .map(reminderMapper::toDto)
                .toList();
    }

    public ReminderDateDto updateReminderDate(ReminderDateUpdateDto reminderDto) {
        var reminderDate = reminderMapper.toEntity(reminderDto);
        return reminderMapper.toDto(reminderDateRepository.save(reminderDate));
    }

    public void deleteReminderDate(UUID reminderDateId) {
        reminderDateRepository.deleteById(reminderDateId);
    }
}
