package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.szczepanskimichal.model.reminder.Reminder;
import work.szczepanskimichal.model.reminder.ReminderCreateDto;
import work.szczepanskimichal.model.reminder.ReminderCreatedDto;
import work.szczepanskimichal.service.ReminderService;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/v1/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;

    @PostMapping
    public ResponseEntity<ReminderCreatedDto> createReminder(@RequestBody ReminderCreateDto reminderCreateDto) {
        return ResponseEntity.ok(reminderService.createReminder(reminderCreateDto));
    }

    @GetMapping
    public Reminder getReminder(@RequestParam UUID id) {
        return reminderService.getReminderById(id);
    }

    @GetMapping("/all")
    public List<Reminder> getRemindersByOccasion(@RequestParam UUID occasionId) {
        return reminderService.getRemindersByOccasion(occasionId);
    }
}
