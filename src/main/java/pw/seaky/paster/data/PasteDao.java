package pw.seaky.paster.data;

import org.springframework.context.annotation.Bean;
import pw.seaky.paster.model.Paste;

import java.util.List;
import java.util.Optional;


public interface PasteDao {

    @Bean
    String daoName();

    Paste addPaste(Paste paste);

    Optional<Paste> getPaste(String id);

    int deletePaste(String id);

    List<Paste> getPastes();

}
