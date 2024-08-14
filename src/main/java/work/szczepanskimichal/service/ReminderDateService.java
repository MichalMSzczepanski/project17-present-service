package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDate;
import work.szczepanskimichal.model.reminder.date.ReminderDateCreateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateDto;
import work.szczepanskimichal.model.reminder.date.ReminderDateUpdateDto;
import work.szczepanskimichal.repository.ReminderDateRepository;
import work.szczepanskimichal.service.cache.ReminderDateCacheService;

import java.util.*;

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
        return reminderMapper.toDto(persistedReminder);
    }

    public ReminderDateDto getReminderDateById(UUID id) {
        return reminderMapper.toDto(reminderDateRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id)));
    }

    public Set<ReminderDate> getReminderDatesForNext24h() {
        Calendar calendar = Calendar.getInstance();

        // Start of the day
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        // End of the day
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = calendar.getTime();

        // Fetch the reminders from the repository
        return reminderDateRepository.getReminderDatesForNext24h(startOfDay, endOfDay);
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
        if (isDateSetUntilMidnight(reminderDto.getDate())) {
            reminderDateCacheService.removeReminderDateFromCache(reminderDto.getId());
        }
        var reminderDate = reminderMapper.toEntity(reminderDto);
        return reminderMapper.toDto(reminderDateRepository.save(reminderDate));
    }

    public void deleteReminderDate(UUID reminderDateId) {
        reminderDateCacheService.removeReminderDateFromCache(reminderDateId);
        reminderDateRepository.deleteById(reminderDateId);
    }

    public static boolean isDateSetUntilMidnight(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long time = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long midnightTime = calendar.getTimeInMillis();
        return time <= midnightTime;
    }
}
