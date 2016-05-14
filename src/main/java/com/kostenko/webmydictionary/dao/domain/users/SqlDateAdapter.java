package com.kostenko.webmydictionary.dao.domain.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;

public class SqlDateAdapter extends XmlAdapter<String, Date> {
    private static final Logger LOG = LoggerFactory.getLogger(SqlDateAdapter.class);

    @Override
    public Date unmarshal(String v) throws Exception {
        Date sqlDate = null;
        try {
            sqlDate = Date.valueOf(v);
        } catch (IllegalArgumentException ex) {
            LOG.warn("Error parsing a next string with date: " + v, ex);
            throw ex;
        }
        return sqlDate;
    }

    @Override
    public String marshal(Date date) throws Exception {
        return date.toString();
    }

}
