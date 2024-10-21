package work.szczepanskimichal.service.reminder;

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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReminderDateService {

    private final ReminderDateRepository reminderDateRepository;
    private final ReminderService reminderService;
    private final ReminderDateMapper reminderMapper;
    private final ReminderDateCacheService reminderDateCacheService;

    public ReminderDateDto createReminderDate(ReminderDateCreateDto reminderCreateDto) {
        isDateSetAfterMidnightWithQuarterHourCheck(reminderCreateDto.getDate());
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
        var startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        var endOfDay = startOfDay.plusDays(1);
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
        if (isDateSetAfterMidnightWithQuarterHourCheck(Date.from((reminderDto.getDate().atZone(ZoneId.systemDefault()).toInstant())))) {
            reminderDateCacheService.removeReminderDateFromCache(reminderDto.getId());
        }
        var reminderDate = reminderMapper.toEntity(reminderDto);
        return reminderMapper.toDto(reminderDateRepository.save(reminderDate));
    }

    public void deleteReminderDate(UUID id) {
        var reminder = reminderDateRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        if (isDateSetAfterMidnightWithQuarterHourCheck(Date.from((reminder.getDate().atZone(ZoneId.systemDefault()).toInstant())))) {
            reminderDateCacheService.removeReminderDateFromCache(reminder.getId());
        }
        reminderDateRepository.deleteById(id);
    }

    public void deleteExpiredReminderDates() {
        reminderDateRepository.deleteReminderDatesByDateBefore(LocalDateTime.now());
    }

    public boolean isDateSetAfterMidnightWithQuarterHourCheck(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int minutes = calendar.get(Calendar.MINUTE);
        if (minutes != 0 && minutes != 15 && minutes != 30 && minutes != 45) {
            return false;
        }

        long time = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long midnightTime = calendar.getTimeInMillis();

        return time > midnightTime;
    }

    public void isDateSetAfterMidnightWithQuarterHourCheck(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("LocalDateTime cannot be null");
        }

        LocalTime time = localDateTime.toLocalTime();

        int minutes = time.getMinute();
        if (minutes != 0 && minutes != 15 && minutes != 30 && minutes != 45) {
            throw new IllegalArgumentException("ReminderDate must be set in 15-minute intervals");
        }

        if (!time.isAfter(LocalTime.MIDNIGHT)) {
            throw new IllegalArgumentException("ReminderDate must be set after midnight");
        }
    }


}
