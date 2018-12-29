package com.lazy.tcc.core.mapper;

import com.lazy.tcc.core.Participant;
import com.lazy.tcc.core.entity.ParticipantEntity;
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
public interface ParticipantMapper {

    ParticipantMapper INSTANCE = Mappers.getMapper(ParticipantMapper.class);

    @Mappings(
            {
                    @Mapping(target = "txId", source = "to.txId"),
                    @Mapping(target = "id", source = "to.id"),
                    @Mapping(target = "appKey", source = "to.appKey"),
                    @Mapping(target = "createTime", source = "to.createTime"),
                    @Mapping(target = "lastUpdateTime", source = "to.lastUpdateTime"),
                    @Mapping(target = "version", source = "to.version"),
                    @Mapping(target = "confirmMethodInvoker", source = "to.confirmMethodInvoker"),
                    @Mapping(target = "cancelMethodInvoker", source = "to.cancelMethodInvoker"),
            }
    )
    Participant from(ParticipantEntity to);

    @Mappings(
            {
                    @Mapping(target = "txId", source = "from.txId"),
                    @Mapping(target = "id", source = "from.id"),
                    @Mapping(target = "appKey", source = "from.appKey"),
                    @Mapping(target = "createTime", source = "from.createTime"),
                    @Mapping(target = "lastUpdateTime", source = "from.lastUpdateTime"),
                    @Mapping(target = "version", source = "from.version"),
                    @Mapping(target = "confirmMethodInvoker", source = "from.confirmMethodInvoker"),
                    @Mapping(target = "cancelMethodInvoker", source = "from.cancelMethodInvoker"),
            }
    )
    ParticipantEntity to(Participant from);

}
