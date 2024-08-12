package work.szczepanskimichal.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.model.reminder.date.ReminderDate;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepository;

@Service
@RequiredArgsConstructor
public class ReminderDateCacheService {

    private final ReminderDateCacheRepository reminderDateCacheRepository;
    private final ReminderDateMapper reminderMapper;

    public void cacheReminderDate(ReminderDate reminderDate) {
        var reminderDateCache = reminderMapper.toCache(reminderDate);
        reminderDateCacheRepository.addReminderDate(reminderDateCache);
    }

}
