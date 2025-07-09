package es.udc.tfg.app.model.Noteline;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class NotelineDaoImpl extends GenericDaoImpl<Noteline, Long> implements NotelineDao {
}
