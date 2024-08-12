package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateUpdateDto;
import work.szczepanskimichal.repository.ReminderDateRepository;
import work.szczepanskimichal.service.cache.ReminderDateCacheService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderDateService {

    private final ReminderDateRepository reminderDateRepository;
    private final ReminderService reminderService;
    private final ReminderDateMapper reminderMapper;
    private final ReminderDateCacheService reminderDateCacheService;

    public ReminderDateDto createReminderDate(ReminderDateCreateDto reminderCreateDto) {
        var parentReminder = reminderService.getReminderById(reminderCreateDto.getReminderId());
        var reminder = reminderMapper.toEntity(reminderCreateDto)
                .toBuilder()
                .reminder(parentReminder)
                .build();
        var persistedReminder = reminderDateRepository.save(reminder);
        reminderDateCacheService.cacheReminderDate(persistedReminder);
        return reminderMapper.toDto(persistedReminder);
    }

    public ReminderDateDto getReminderDateById(UUID id) {
        return reminderMapper.toDto(reminderDateRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id)));
    }

    public List<ReminderDateDto> getReminderDatesByReminder(UUID id) {
        var reminders = reminderDateRepository.getReminderDatesByReminderId(id);
        if (reminders.isEmpty()) {
            throw new DataNotFoundException(id, "Reminder");
        }
        return reminders.stream()
                .map(reminderMapper::toDto)
                .toList();
    }

    public ReminderDateDto updateReminderDate(ReminderDateUpdateDto reminderDto) {
        var reminderDate = reminderMapper.toEntity(reminderDto);
        //todo update cached reminderdate
        return reminderMapper.toDto(reminderDateRepository.save(reminderDate));
    }

    public void deleteReminderDate(UUID reminderDateId) {
        //todo delete reminderdate from cache
        reminderDateRepository.deleteById(reminderDateId);
    }
}
