package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public ReminderCreatedDto createReminder(ReminderCreateDto reminderCreateDto) {
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
}
