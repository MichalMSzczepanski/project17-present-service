package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.context.UserContext;
import work.szczepanskimichal.exception.OwnerMissmatchException;
import work.szczepanskimichal.mapper.ReminderMapper;
import work.szczepanskimichal.model.reminder.Reminder;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderCreatedDto;
import work.szczepanskimichal.repository.ReminderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final OccasionService occasionService;
    private final ReminderMapper reminderMapper;
    private final UserContext userContext;

    public ReminderCreatedDto createReminder(ReminderCreateDto reminderCreateDto) {
        validateReminderOwner(reminderCreateDto.getOwner());
        var parentOccasion = occasionService.getOccasionById(reminderCreateDto.getOccasionId());
        var reminder = reminderMapper.toEntity(reminderCreateDto)
                .toBuilder()
                .occasion(parentOccasion)
                .build();
        return reminderMapper.toDto(reminderRepository.save(reminder));
    }

    public Reminder getReminderById(UUID id) {
        return reminderRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Reminder> getRemindersByOccasion(UUID occasionId) {
        return reminderRepository.getRemindersByOccasionId(occasionId);
    }

    private void validateReminderOwner(UUID personOwnerId) {
        if (personOwnerId == userContext.getUserId()) {
            throw new OwnerMissmatchException(userContext.getUserId(), personOwnerId);
        }
    }
}
