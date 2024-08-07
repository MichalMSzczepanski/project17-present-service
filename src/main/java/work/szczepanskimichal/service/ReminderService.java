package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.OwnerMissmatchException;
import work.szczepanskimichal.mapper.OccasionMapper;
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
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final OccasionService occasionService;
    private final ReminderMapper reminderMapper;
    private final OccasionMapper occasionMapper;
    private final UserContext userContext;

    public ReminderDto createReminder(ReminderCreateDto reminderCreateDto) {
        validateReminderOwner(reminderCreateDto.getOwner());
        var parentOccasion =occasionService.getOccasionById(reminderCreateDto.getOccasionId());
        var reminder = reminderMapper.toEntity(reminderCreateDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return reminderMapper.toDto(reminderRepository.save(reminder));
    }

    public ReminderDto getReminderDtoById(UUID id) {
        return reminderMapper.toDto(reminderRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Reminder getReminderById(UUID id) {
        return reminderRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<ReminderDto> getRemindersByOccasion(UUID occasionId) {
        var reminders = reminderRepository.getRemindersByOccasionId(occasionId);
        return reminders.stream()
                .map(reminderMapper::toDto)
                .toList();
    }

    public ReminderDto updateReminder(ReminderUpdateDto reminderDto) {
        var reminder = reminderMapper.toEntity(reminderDto);
        return reminderMapper.toDto(reminderRepository.save(reminder));
    }

    public void deleteReminder(UUID id) {
        var reminder = reminderRepository.findById(id).orElseThrow(RuntimeException::new);
        validateReminderOwner(reminder.getOwner());
        reminderRepository.deleteById(id);
    }

    private void validateReminderOwner(UUID personOwnerId) {
        if (personOwnerId == userContext.getUserId()) {
            throw new OwnerMissmatchException(userContext.getUserId(), personOwnerId);
        }
    }
}
