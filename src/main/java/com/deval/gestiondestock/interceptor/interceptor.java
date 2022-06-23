package com.deval.gestiondestock.interceptor;

import org.hibernate.EmptyInterceptor;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.Locale;

public class interceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        if(StringUtils.hasLength(sql) && sql.toLowerCase().startsWith("select")){
            //select utilisateu0_. ( on extraire le nom de l'entité à partir de la position select)
            final String entityName = sql.substring(7, sql.indexOf("."));
            final String idEntreprise = MDC.get("idEntreprise");
            if (StringUtils.hasLength(entityName)
            && !entityName.toLowerCase().contains("entreprise")
            && !entityName.toLowerCase().contains("roles")
            && StringUtils.hasLength(idEntreprise)){
                if (sql.contains("where")){
                    sql = sql + " and " + entityName + ".idEntreprise = " + idEntreprise;
                } else {
                    sql = sql + " where " + entityName + ".idEntreprise = " + idEntreprise;
                }
            }

        }
        return super.onPrepareStatement(sql);
    }
}
