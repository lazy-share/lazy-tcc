package com.lazy.tcc.core.repository.jdbc;

import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.entity.AppKeyEntity;
import com.lazy.tcc.core.exception.TransactionCrudException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.support.AbstractAppKeyRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * MysqlAppKeyRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class MysqlAppKeyRepository extends AbstractAppKeyRepository {

    private static final Logger logger = LoggerFactory.getLogger(MysqlAppKeyRepository.class);

    @Override
    public int createTable() {

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = this.getConnection();

            //checked tx table is exists
            String tableIsExistsSql =
                    "select count(*) as is_exists from information_schema.TABLES t where t.TABLE_SCHEMA = ? and t.TABLE_NAME = ?";
            stmt = connection.prepareStatement(tableIsExistsSql);

            stmt.setString(1, SpiConfiguration.getInstance().getTxDatabaseName());
            stmt.setString(2, SpiConfiguration.getInstance().getAppKeyTableName());
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int isExists = resultSet.getInt("is_exists");
                if (isExists > 0) {
                    logger.info(String.format("appKey table %s exists", SpiConfiguration.getInstance().getAppKeyTableName()));

                    return 1;
                }
            }

            logger.info(String.format("appKey table %s not exists, now create it", SpiConfiguration.getInstance().getAppKeyTableName()));

            String sql = "CREATE TABLE `" + SpiConfiguration.getInstance().getAppKey() + "` (" +
                    "  `app_key` varchar (32) NOT NULL COMMENT '主键'," +
                    "  `app_desc` int(60) NOT NULL COMMENT '描述'," +
                    "  `create_time` datetime NOT NULL COMMENT '创建时间'" +
                    "  PRIMARY KEY (`app_key`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='lazy-tcc应用接入表'";

            stmt = connection.prepareStatement(sql);
            stmt.execute(sql);

            return 1;
        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            this.releaseConnection(connection);
        }
    }

    @Override
    public int insert(AppKeyEntity entity) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getAppKeyTableName() +
                    " (app_key,app_desc,create_time) VALUES (?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setString(1, entity.getAppKey());
            stmt.setString(2, entity.getAppDesc());

            stmt.setString(3, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));

            return stmt.executeUpdate();

        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int update(AppKeyEntity entity) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            stmt = connection.prepareStatement("update " + SpiConfiguration.getInstance().getAppKeyTableName() +
                    " set " + "app_desc = ?");

            stmt.setString(1, entity.getAppDesc());

            return stmt.executeUpdate();

        } catch (Throwable e) {
            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int delete(String appKey) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "delete from " + SpiConfiguration.getInstance().getAppKeyTableName() + " where app_key = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setString(1, appKey);

            return stmt.executeUpdate();

        } catch (SQLException e) {

            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public AppKeyEntity findById(String appKey) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        AppKeyEntity entity = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getTxTableName() + " where app_key = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setString(1, appKey);

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                entity = this.getRow(resultSet);
            }

        } catch (Exception e) {

            throw new TransactionCrudException(e);
        } finally {

            closeResultSet(resultSet);
            closeStatement(stmt);
            this.releaseConnection(connection);
        }

        return entity;
    }

    @Override
    public boolean exists(String aString) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private AppKeyEntity getRow(ResultSet resultSet) throws Exception {
        return new AppKeyEntity()
                .setAppKey(resultSet.getString("app_key"))
                .setCreateTime(resultSet.getString("create_time"))
                .setAppDesc(resultSet.getString("app_desc"));
    }

}
