package com.lazy.tcc.core.repository.jdbc;

import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.entity.TransactionEntity;
import com.lazy.tcc.core.exception.TransactionCrudException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.support.AbstractTransactionRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * MysqlTransactionRepository Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/13.
 */
public class MysqlTransactionRepository extends AbstractTransactionRepository {

    private static final Logger logger = LoggerFactory.getLogger(MysqlTransactionRepository.class);

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
            stmt.setString(2, SpiConfiguration.getInstance().getTxTableName());
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int isExists = resultSet.getInt("is_exists");
                if (isExists > 0) {
                    logger.info(String.format("transaction table %s exists", SpiConfiguration.getInstance().getTxTableName()));

                    return 1;
                }
            }

            logger.info(String.format("transaction table %s not exists, now create it", SpiConfiguration.getInstance().getTxTableName()));

            String sql = "CREATE TABLE `" + SpiConfiguration.getInstance().getTxTableName() + "` (" +
                    "  `tx_id` bigint(20) NOT NULL COMMENT '主键'," +
                    "  `tx_phase` int(5) NOT NULL COMMENT '事务阶段 try,confirm,cancel'," +
                    "  `retry_count` int(5) NOT NULL COMMENT '重试次数'," +
                    "  `version` bigint(20) NOT NULL COMMENT '乐观锁版本号'," +
                    "  `app_key` varchar(32) NOT NULL COMMENT '应用key'," +
                    "  `create_time` datetime NOT NULL COMMENT '创建时间'," +
                    "  `last_update_time` datetime NOT NULL COMMENT '最后更新时间'," +
                    "  PRIMARY KEY (`tx_id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='lazy-tcc事务日志表'";

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
    public int insert(TransactionEntity transaction) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getTxTableName() +
                    " (tx_id,retry_count,create_time,last_update_time,version,tx_phase,app_key) VALUES (?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, transaction.getTxId());

            stmt.setInt(2, transaction.getRetryCount());
            stmt.setString(3, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setString(4, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setLong(5, transaction.getVersion());
            stmt.setInt(6, transaction.getTxPhase().getVal());
            stmt.setString(7, transaction.getAppKey());

            return stmt.executeUpdate();

        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int update(TransactionEntity transaction) {
        Connection connection = null;
        PreparedStatement stmt = null;

        String lastUpdateTime = transaction.getLastUpdateTime();
        long currentVersion = transaction.getVersion();

        transaction.updateLastUpdateTime();
        transaction.updateVersion();

        try {
            connection = this.getConnection();

            stmt = connection.prepareStatement("update " + SpiConfiguration.getInstance().getTxTableName() +
                    " set " + "tx_phase = ?,last_update_time = ?, retry_count = ?," +
                    "version = version + 1 " + "where tx_id = ? and  version = ?");

            stmt.setInt(1, transaction.getTxPhase().getVal());
            stmt.setString(2, transaction.getLastUpdateTime());

            stmt.setInt(3, transaction.getRetryCount());
            stmt.setLong(4, transaction.getTxId());
            stmt.setLong(5, currentVersion);

            return stmt.executeUpdate();

        } catch (Throwable e) {
            transaction.setLastUpdateTime(lastUpdateTime);
            transaction.setVersion(currentVersion);
            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int delete(Long id) {

        Connection connection = null;
        PreparedStatement stmt = null;

        try {

            PARTICIPANT_REPOSITORY.deleteByTxId(id);

            connection = this.getConnection();

            String builder = "delete from " + SpiConfiguration.getInstance().getTxTableName() + " where tx_id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, id);

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
    public TransactionEntity findById(Long id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        TransactionEntity transaction = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getTxTableName() + " where tx_id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, id);

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                transaction = this.getRow(resultSet);
            }

        } catch (Exception e) {

            throw new TransactionCrudException(e);
        } finally {

            closeResultSet(resultSet);
            closeStatement(stmt);
            this.releaseConnection(connection);
        }

        return transaction;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private TransactionEntity getRow(ResultSet resultSet) throws Exception {
        return new TransactionEntity()
                .setLastUpdateTime(resultSet.getString("last_update_time"))
                .setCreateTime(resultSet.getString("create_time"))
                .setVersion(resultSet.getLong("version"))
                .setAppKey(resultSet.getString("app_key"))
                .setTxPhase(TransactionPhase.valueOf(resultSet.getInt("tx_phase")))
                .setRetryCount(resultSet.getInt("retry_count"))
                .setTxId(resultSet.getLong("tx_id"));
    }

    @Override
    public List<TransactionEntity> findAllFailure() {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<TransactionEntity> list = new ArrayList<>();
        TransactionEntity transaction = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getTxTableName()
                    + " where create_time <= ? and retry_count < ? and app_key =?";
            stmt = connection.prepareStatement(builder);

            stmt.setString(1,
                    DateUtils.getBeforeByMinuteTime(SpiConfiguration.getInstance().getCompensationMinuteInterval(),
                            DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setInt(2, SpiConfiguration.getInstance().getRetryCount());
            stmt.setString(3, SpiConfiguration.getInstance().getAppKey());

            resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                list.add(this.getRow(resultSet));
            }

        } catch (Exception e) {

            throw new TransactionCrudException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }

        return list;
    }


}
