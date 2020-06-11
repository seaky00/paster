package pw.seaky.paster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pw.seaky.paster.data.PasteDao;
import pw.seaky.paster.exception.PasteNotFoundException;
import pw.seaky.paster.model.Paste;
import pw.seaky.paster.utils.ExpiryOption;

import java.util.ArrayList;
import java.util.List;


@Service

public class PasteService {

    private final PasteDao dao;

    private List<Paste> pasteList = new ArrayList<>();
    @Autowired
    public PasteService(@Qualifier("mongodas") PasteDao dao) {
        this.dao = dao;
    }

    public PasteDao getDao() {
        return dao;
    }



    @Cacheable(cacheNames = "pastes")
    public Paste getPaste(String id) throws PasteNotFoundException {
        if(dao.getPaste(id).isPresent()) {
            return dao.getPaste(id).get();
        }
        throw new PasteNotFoundException("Could not find paste!");
    }

    @Cacheable(cacheNames = "pastes")
    public Paste addPaste(Paste paste) {
        paste.setBody(paste.getUnsplit().replaceAll("/r", "").split("\n"));

        dao.addPaste(paste);
        return paste;
    }

    public void deletePaste(String id) {
        dao.deletePaste(id);
    }


    private void genPastes() {

        Paste not_found = new Paste();
        not_found.setUnsplit("The page you were looking for does not exist (anymore)");
        not_found.setId("404");
        not_found.setTitle("404");
        not_found.setOption(ExpiryOption.NEVER);
        addPaste(not_found);
    }

    public List<Paste> getPasteList() {
        return dao.getPastes();
    }
}
