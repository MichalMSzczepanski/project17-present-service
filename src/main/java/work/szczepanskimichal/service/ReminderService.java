package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.DataNotFoundException;
import work.szczepanskimichal.mapper.ReminderMapper;
import work.szczepanskimichal.model.reminder.Reminder;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderDto;
import work.szczepanskimichal.model.reminder.ReminderUpdateDto;
import work.szczepanskimichal.repository.ReminderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final OccasionService occasionService;
    private final ReminderMapper reminderMapper;
    private final UserContext userContext;
    private final ValidationService validationService;

    public ReminderDto createReminder(ReminderCreateDto reminderCreateDto) {
        validationService.validateOwner(reminderCreateDto.getOwner(), userContext);
        var parentOccasion = occasionService.getOccasionById(reminderCreateDto.getOccasionId());
        var reminder = reminderMapper.toEntity(reminderCreateDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return reminderMapper.toDto(reminderRepository.save(reminder));
    }

    public ReminderDto getReminderDtoById(UUID id) {
        var reminder = reminderRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(reminder.getOwner(), userContext);
        return reminderMapper.toDto(reminder);
    }

    public Reminder getReminderById(UUID id) {
        return reminderRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
    }

    public List<ReminderDto> getRemindersByOccasion(UUID occasionId) {
        var reminders = reminderRepository.getRemindersByOccasionId(occasionId);
        return reminders.stream()
                .map(reminderMapper::toDto)
                .toList();
    }

    public ReminderDto updateReminder(ReminderUpdateDto reminderUpdateDto) {
        validationService.validateOwner(reminderUpdateDto.getOwner(), userContext);
        var reminder = reminderMapper.toEntity(reminderUpdateDto);
        return reminderMapper.toDto(reminderRepository.save(reminder));
    }

    public void deleteReminder(UUID id) {
        var reminder = reminderRepository.findById(id).orElseThrow(() -> new DataNotFoundException(id));
        validationService.validateOwner(reminder.getOwner(), userContext);
        reminderRepository.deleteById(id);
        log.info("deleted reminder: {}", reminder.getId());
    }

}
