package com.lazy.tcc.core.repository.support;

import com.lazy.tcc.core.entity.support.BasicEntity;
import com.lazy.tcc.core.exception.ConnectionIOException;
import com.lazy.tcc.core.exception.TransactionCrudException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.Repository;
import com.lazy.tcc.core.serializer.Serialization;
import com.lazy.tcc.core.serializer.SerializationFactory;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * AbstractDataSourceRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
public abstract class AbstractDataSourceRepository<E extends BasicEntity, ID extends Serializable> implements Repository<E, ID> {

    private DataSource dataSource;
    protected static final Serialization serialization = SerializationFactory.create();

    public DataSource getDataSource() {
        return dataSource;
    }

    public AbstractDataSourceRepository<E, ID> setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    protected Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionIOException(e);
        }
    }

    protected void releaseConnection(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            throw new TransactionCrudException(e);
        }
    }

    protected void closeStatement(Statement stmt) {
//        try {
//            if (stmt != null && !stmt.isClosed()) {
//                stmt.close();
//            }
//        } catch (Exception ex) {
//            throw new TransactionCrudException(ex);
//        }
    }

    protected void closeResultSet(ResultSet rst) {
//        try {
//            if (rst != null && !rst.isClosed()) {
//                rst.close();
//            }
//        } catch (Exception ex) {
//            throw new TransactionCrudException(ex);
//        }
    }
}
