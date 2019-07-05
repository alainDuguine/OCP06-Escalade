package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Entitie {
    public abstract boolean check();
    public abstract void hydrate(HttpServletRequest req);
    public abstract Map<String, String> checkErreurs(EntityRepository dao);
}
