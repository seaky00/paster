package pw.seaky.paster.data;

import org.springframework.stereotype.Repository;
import pw.seaky.paster.model.Paste;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakePasteDAS implements PasteDao {


    private final static List<Paste> list = new ArrayList<>();


    @Override
    public String daoName() {
        return "Fake DAS";
    }

    @Override
    public Boolean exists(String id) {
        return null;
    }

    @Override
    public Paste addPaste(Paste paste) {
        String id = paste.getId();
        if (!this.getPaste(id).isPresent()) {
            list.add(paste);
            return paste;
        }
        return null;
    }

    @Override
    public Optional<Paste> getPaste(String id) {
        return list.stream().filter(text -> text.getId().equals(id)).findFirst();
    }

    @Override
    public int deletePaste(String id) {
        Optional<Paste> text = this.getPaste(id);
        if (!text.isPresent()) {
            return 0;
        }
        list.remove(text.get());
        return 1;
    }

    @Override
    public List<Paste> getPastes() {
        return list;
    }

}