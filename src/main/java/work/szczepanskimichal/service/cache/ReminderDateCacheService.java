package work.szczepanskimichal.service.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.mapper.ReminderDateMapper;
import work.szczepanskimichal.mapper.ReminderDateMapperImpl;
import work.szczepanskimichal.model.reminder.date.ReminderDate;
import work.szczepanskimichal.model.reminder.date.ReminderDateCache;
import work.szczepanskimichal.repository.cache.ReminderDateCacheRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderDateCacheService {

    private final ReminderDateCacheRepository reminderDateCacheRepository;
    private final ReminderDateMapper reminderMapper;

    public void clearCache() {
        reminderDateCacheRepository.clearCache();
        log.info("Cache cleared");
    }

    public void cacheReminderDate(ReminderDate reminderDate) {
        var reminderDateCache = reminderMapper.toCache(reminderDate);
        reminderDateCacheRepository.addReminderDateCache(reminderDateCache);
    }

    public Set<ReminderDateCache> getReminderDateCachesForNextFifteenMinutes() {
        return reminderDateCacheRepository.getReminderDateCachesForNextFifteenMinutes();
    }

    public void addReminderDateCaches(Set<ReminderDateCache> reminderDateCaches) {
        reminderDateCacheRepository.addReminderDateCaches(reminderDateCaches);
    }

    public void removeReminderDateFromCache(UUID id) {
        reminderDateCacheRepository.removeReminderDateCache(id);
    }
}
