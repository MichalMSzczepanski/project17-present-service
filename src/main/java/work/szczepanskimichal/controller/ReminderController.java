package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.reminder.Reminder;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderDto;
import work.szczepanskimichal.model.reminder.ReminderUpdateDto;
import work.szczepanskimichal.service.ReminderService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping()
    public ResponseEntity<ReminderDto> createReminder(@RequestBody ReminderCreateDto reminderCreateDto) {
        return ResponseEntity.ok(reminderService.createReminder(reminderCreateDto));
    }

    @GetMapping
    public Reminder getReminder(@RequestParam UUID id) {
        return reminderService.getReminderById(id);
    }

    @GetMapping("/{occasionId}")
    public List<Reminder> getRemindersByOccasion(@PathVariable UUID occasionId) {
        return reminderService.getRemindersByOccasion(occasionId);
    }

    @PatchMapping()
    public ReminderDto updateReminder(@RequestBody ReminderUpdateDto reminderDto) {
        return reminderService.updateReminder(reminderDto);
    }

    @DeleteMapping("/{reminderId}")
    public void deleteReminder(@PathVariable UUID reminderId) {
        reminderService.deleteReminder(reminderId);
    }
}
