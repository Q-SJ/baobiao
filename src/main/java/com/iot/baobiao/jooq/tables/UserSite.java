/**
 * This class is generated by jOOQ
 */
package com.iot.baobiao.jooq.tables;


import com.iot.baobiao.jooq.Keys;
import com.iot.baobiao.jooq.Nutch;
import com.iot.baobiao.jooq.tables.records.UserSiteRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserSite extends TableImpl<UserSiteRecord> {

    private static final long serialVersionUID = 1829608211;

    /**
     * The reference instance of <code>nutch.user_site</code>
     */
    public static final UserSite USER_SITE = new UserSite();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSiteRecord> getRecordType() {
        return UserSiteRecord.class;
    }

    /**
     * The column <code>nutch.user_site.id</code>.
     */
    public final TableField<UserSiteRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>nutch.user_site.user_id</code>.
     */
    public final TableField<UserSiteRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>nutch.user_site.site_id</code>.
     */
    public final TableField<UserSiteRecord, Integer> SITE_ID = createField("site_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>nutch.user_site.sitename</code>.
     */
    public final TableField<UserSiteRecord, String> SITENAME = createField("sitename", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>nutch.user_site.start_url</code>.
     */
    public final TableField<UserSiteRecord, String> START_URL = createField("start_url", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "");

    /**
     * Create a <code>nutch.user_site</code> table reference
     */
    public UserSite() {
        this("user_site", null);
    }

    /**
     * Create an aliased <code>nutch.user_site</code> table reference
     */
    public UserSite(String alias) {
        this(alias, USER_SITE);
    }

    private UserSite(String alias, Table<UserSiteRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSite(String alias, Table<UserSiteRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Nutch.NUTCH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserSiteRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_SITE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserSiteRecord> getPrimaryKey() {
        return Keys.KEY_USER_SITE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserSiteRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSiteRecord>>asList(Keys.KEY_USER_SITE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSite as(String alias) {
        return new UserSite(alias, this);
    }

    /**
     * Rename this table
     */
    public UserSite rename(String name) {
        return new UserSite(name, null);
    }
}