package com.lazy.tcc.core.mapper;

import com.lazy.tcc.core.Transaction;
import com.lazy.tcc.core.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * TransactionMapper Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/17.
 */
@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mappings(
            {
                    @Mapping(target = "txId", source = "to.txId"),
                    @Mapping(target = "txPhase", source = "to.txPhase"),
                    @Mapping(target = "retryCount", source = "to.retryCount"),
                    @Mapping(target = "createTime", source = "to.createTime"),
                    @Mapping(target = "lastUpdateTime", source = "to.lastUpdateTime"),
                    @Mapping(target = "version", source = "to.version"),
            }
    )
    Transaction from(TransactionEntity to);

    @Mappings(
            {
                    @Mapping(target = "txId", source = "from.txId"),
                    @Mapping(target = "txPhase", source = "from.txPhase"),
                    @Mapping(target = "retryCount", source = "from.retryCount"),
                    @Mapping(target = "createTime", source = "from.createTime"),
                    @Mapping(target = "lastUpdateTime", source = "from.lastUpdateTime"),
                    @Mapping(target = "version", source = "from.version"),
            }
    )
    TransactionEntity to(Transaction from);

}
