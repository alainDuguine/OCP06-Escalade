package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public abstract class Entitie {
    public abstract void hydrate(HttpServletRequest req);
    public abstract Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req);
//    public abstract List<Entitie> checkIfExist(EntityRepository dao, HttpServletRequest req);
}
