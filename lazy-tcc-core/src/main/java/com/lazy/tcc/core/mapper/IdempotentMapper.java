package com.lazy.tcc.core.mapper;

import com.lazy.tcc.core.Idempotent;
import com.lazy.tcc.core.entity.IdempotentEntity;
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
public interface IdempotentMapper {

    IdempotentMapper INSTANCE = Mappers.getMapper(IdempotentMapper.class);

    @Mappings(
            {
                    @Mapping(target = "pk", source = "to.pk"),
                    @Mapping(target = "businessRec", source = "to.businessRec"),
                    @Mapping(target = "createTime", source = "to.createTime"),
            }
    )
    Idempotent from(IdempotentEntity to);

    @Mappings(
            {
                    @Mapping(target = "pk", source = "from.pk"),
                    @Mapping(target = "businessRec", source = "from.businessRec"),
                    @Mapping(target = "createTime", source = "from.createTime"),
            }
    )
    IdempotentEntity to(Idempotent from);

}
