package pw.seaky.paster.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pw.seaky.paster.model.Paste;
import pw.seaky.paster.service.PasteService;
import pw.seaky.paster.utils.ExpiryOption;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.logging.Logger;

@Component
public class PasteScheduler {




    @Autowired
    private PasteService pasteService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    CacheManager cache;

    @Scheduled(fixedRate = 1200000)
    public void onSchedule() throws ParseException {
        Logger.getAnonymousLogger().info("Checking for pastes to remove...");
        ;
        pasteService.getPasteList().addAll(mongoTemplate.findAll(Paste.class, "pastes"));
        for (Paste paste : pasteService.getPasteList()) {
            if (paste.getOption() != ExpiryOption.NEVER) {
                String created = paste.getCreated();
                LocalDateTime createdDateTime = LocalDateTime.parse(created);
                if (calc(createdDateTime, paste.getOption())) {
                    pasteService.deletePaste(paste.getId());
                    Logger.getAnonymousLogger().info("Removed " + paste.getId());
                }
            }



        }
    }

    private boolean calc(LocalDateTime createdDate, ExpiryOption option) throws ParseException {

        LocalDateTime calculatedDate = createdDate.plus(Period.ofDays(option.getDays()));
        return calculatedDate.isBefore(LocalDateTime.now());
    }
}
