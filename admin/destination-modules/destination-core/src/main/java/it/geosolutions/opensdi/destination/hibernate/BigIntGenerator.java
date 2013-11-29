/*
 *  OpenSDI Manager
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.opensdi.destination.hibernate;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.PostInsertIdentityPersister;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;

/**
 * Customized generator for testing proposal (just insert on test)
 * 
 * @author adiaz
 */
public class BigIntGenerator extends IdentityGenerator {

public static class BigIntDelegate extends GetGeneratedKeysDelegate {

private static Random random = new Random();

private static Long lastNumber;

public BigIntDelegate(PostInsertIdentityPersister arg0, Dialect arg1) {
    super(arg0, arg1);
}

@Override
protected PreparedStatement prepare(String insertSQL, SessionImplementor session)
        throws SQLException {
    // TODO: fix it: use other generator
    lastNumber = random.nextLong();
    insertSQL = insertSQL.replace("null", lastNumber + "");

    return super.prepare(insertSQL, session);
}

@Override
public Serializable executeAndExtract(PreparedStatement insert)
        throws SQLException {
    insert.executeUpdate();
    ResultSet rs = null;
    try {
        rs = insert.getGeneratedKeys();
        if (!rs.next()) {
            // TODO: fix it: use other generator
            return new BigInteger(lastNumber + "");
            // throw new HibernateException(
            // "The database returned no natively generated identity value" );
        }
        return rs.getBigDecimal(1).toBigIntegerExact();
        // ...or whatever type your identity field is
    } finally {
        if (rs != null)
            rs.close();
    }
}
}

@Override
public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(
        PostInsertIdentityPersister persister, Dialect dialect,
        boolean isGetGeneratedKeysEnabled) throws HibernateException {
    InsertGeneratedIdentifierDelegate d = super
            .getInsertGeneratedIdentifierDelegate(persister, dialect,
                    isGetGeneratedKeysEnabled);
    if (d instanceof GetGeneratedKeysDelegate)
        d = new BigIntDelegate(persister, dialect);
    return d;
}
}