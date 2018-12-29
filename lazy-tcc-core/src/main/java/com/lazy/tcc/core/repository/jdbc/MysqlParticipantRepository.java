package com.lazy.tcc.core.repository.jdbc;

import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.common.utils.SnowflakeIdWorkerUtils;
import com.lazy.tcc.core.Invoker;
import com.lazy.tcc.core.entity.ParticipantEntity;
import com.lazy.tcc.core.exception.TransactionCrudException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.repository.support.AbstractParticipantRepository;
import com.lazy.tcc.core.spi.SpiConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
public class MysqlParticipantRepository extends AbstractParticipantRepository {

    private static final Logger logger = LoggerFactory.getLogger(MysqlParticipantRepository.class);

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
            stmt.setString(2, SpiConfiguration.getInstance().getParticipantTableName());
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int isExists = resultSet.getInt("is_exists");
                if (isExists > 0) {
                    logger.info(String.format("participant table %s exists", SpiConfiguration.getInstance().getParticipantTableName()));

                    return 1;
                }
            }

            logger.info(String.format("participant table %s not exists, now create it", SpiConfiguration.getInstance().getParticipantTableName()));

            String sql = "CREATE TABLE `" + SpiConfiguration.getInstance().getParticipantTableName() + "` (" +
                    "  `id` bigint(20) NOT NULL COMMENT '主键'," +
                    "  `tx_id` bigint(20) NOT NULL COMMENT '事务外键'," +
                    "  `app_key` bigint(20) NOT NULL COMMENT '应用key'," +
                    "  `confirm_invoker` varbinary(3000) NOT NULL COMMENT '确认调用对象'," +
                    "  `cancel_invoker` varbinary(3000) NOT NULL COMMENT '取消调用对象'," +
                    "  `version` bigint(20) NOT NULL COMMENT '乐观锁版本号'," +
                    "  `create_time` varchar(32) NOT NULL COMMENT '创建时间'," +
                    "  `last_update_time` varchar(32) NOT NULL COMMENT '最后更新时间'," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='lazy-tcc事务参与者表'";

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
    public int insert(ParticipantEntity entity) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "insert into " + SpiConfiguration.getInstance().getParticipantTableName() +
                    " (id,tx_id,confirm_invoker,cancel_invoker," +
                    "app_key,create_time,last_update_time,version) VALUES (?,?,?,?,?,?,?,?)";

            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, SnowflakeIdWorkerUtils.getSingle().nextId());
            stmt.setLong(2, entity.getTxId());

            ByteArrayOutputStream confirmInvoker = new ByteArrayOutputStream(512);
            serialization.serialize(confirmInvoker).writeObject(entity.getConfirmMethodInvoker());
            stmt.setBytes(3, confirmInvoker.toByteArray());

            ByteArrayOutputStream cancelInvoker = new ByteArrayOutputStream(512);
            serialization.serialize(cancelInvoker).writeObject(entity.getCancelMethodInvoker());
            stmt.setBytes(4, cancelInvoker.toByteArray());

            stmt.setString(5, SpiConfiguration.getInstance().getAppKey());
            stmt.setString(6, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setString(7, DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS));
            stmt.setLong(8, entity.getVersion());

            return stmt.executeUpdate();

        } catch (Exception e) {
            throw new TransactionCrudException(e);
        } finally {
            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }

    @Override
    public int update(ParticipantEntity entity) {

        throw new UnsupportedOperationException("participant unsupported update operation");
    }

    @Override
    public int delete(Long id) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "delete from " + SpiConfiguration.getInstance().getParticipantTableName() + " where id = ?";
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
    public ParticipantEntity findById(Long id) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        ParticipantEntity entity = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getParticipantTableName() + " where id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, id);

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
    public boolean exists(Long aLong) {
        return false;
    }

    @SuppressWarnings("unchecked")
    private ParticipantEntity getRow(ResultSet resultSet) throws Exception {
        return new ParticipantEntity()
                .setLastUpdateTime(resultSet.getString("last_update_time"))
                .setCreateTime(resultSet.getString("create_time"))
                .setVersion(resultSet.getLong("version"))
                .setConfirmMethodInvoker(serialization.deserialize(
                        new ByteArrayInputStream(resultSet.getBytes("confirm_invoker")))
                        .readObject(Invoker.class))
                .setCancelMethodInvoker(serialization.deserialize(
                        new ByteArrayInputStream(resultSet.getBytes("cancel_invoker")))
                        .readObject(Invoker.class))
                .setAppKey(resultSet.getString("app_key"))
                .setId(resultSet.getLong("id"))
                .setTxId(resultSet.getLong("tx_id"));
    }

    @Override
    public List<ParticipantEntity> findByTxId(Long txId) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<ParticipantEntity> list = new ArrayList<>();
        ParticipantEntity entity = null;
        try {
            connection = this.getConnection();

            String builder = "select * from " + SpiConfiguration.getInstance().getParticipantTableName()
                    + " where tx_id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, txId);

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

    @Override
    public int deleteByTxId(Long txId) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = this.getConnection();

            String builder = "delete from " + SpiConfiguration.getInstance().getParticipantTableName() + " where tx_id = ?";
            stmt = connection.prepareStatement(builder);

            stmt.setLong(1, txId);

            return stmt.executeUpdate();

        } catch (SQLException e) {

            throw new TransactionCrudException(e);
        } finally {

            closeStatement(stmt);
            this.releaseConnection(connection);
        }
    }


}
