package com.lazy.tcc.core.processor;

import com.lazy.tcc.common.enums.ApplicationRole;
import com.lazy.tcc.common.enums.TransactionPhase;
import com.lazy.tcc.common.utils.DateUtils;
import com.lazy.tcc.core.Idempotent;
import com.lazy.tcc.core.IdempotentContext;
import com.lazy.tcc.core.WeavingPointInfo;
import com.lazy.tcc.core.entity.IdempotentEntity;
import com.lazy.tcc.core.exception.IdempotentInterceptorException;
import com.lazy.tcc.core.logger.Logger;
import com.lazy.tcc.core.logger.LoggerFactory;
import com.lazy.tcc.core.mapper.IdempotentMapper;
import com.lazy.tcc.core.processor.support.AbstractProcessor;
import com.lazy.tcc.core.propagator.IdempotentContextPropagator;
import com.lazy.tcc.core.propagator.IdempotentContextPropagatorSingleFactory;
import com.lazy.tcc.core.repository.IdempotentRepositoryFactory;
import com.lazy.tcc.core.repository.support.AbstractIdempotentRepository;

/**
 * <p>
 * IdempotentProcessor Definition
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/15.
 */
public final class IdempotentProcessor extends AbstractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdempotentProcessor.class);

    private static final AbstractIdempotentRepository IDEMPOTENT_REPOSITORY = IdempotentRepositoryFactory.create();

    private static IdempotentProcessor single;

    public static IdempotentProcessor getSingle() {
        if (single == null) {
            synchronized (IdempotentProcessor.class) {
                if (single == null) {
                    single = new IdempotentProcessor();
                }
            }
        }
        return single;
    }


    @Override
    protected boolean isAccept(WeavingPointInfo pointInfo) {

        return pointInfo.getIdempotent() != null && pointInfo.getIdempotent().applicationRole().equals(ApplicationRole.PROVIDER);
    }

    /**
     * @param pointInfo {@link WeavingPointInfo}
     * @return {@link Object}
     * @throws Throwable
     */
    @Override
    protected Object doProcessor(WeavingPointInfo pointInfo) throws Throwable {

        IdempotentContext context = IdempotentContextPropagatorSingleFactory.create(pointInfo.getIdempotent().propagator()).getIdempotentContext();

        if (context == null) {

            LOGGER.warn("Consumers do not have corresponding idempotent annotations");
            return pointInfo.getJoinPoint().proceed();
        }

        if (context.getTxPhase().equals(TransactionPhase.TRY)) {

            LOGGER.warn("current transaction phase is try, idempotent processor not processor");
            return pointInfo.getJoinPoint().proceed();
        }

        Idempotent idempotent = new Idempotent().setPk(context.getPk());
        IdempotentEntity idempotentEntity = IdempotentMapper.INSTANCE.to(idempotent);

        if (IDEMPOTENT_REPOSITORY.exists(idempotentEntity.getPk())) {

            LOGGER.info(String.format(
                    "The idempotent processor receives repeated processing requests. The current transaction phase is %s and the transaction ID is %s"
                    , String.valueOf(context.getTxPhase().getDesc()), context.getTxId()));

            return null;
        }

        idempotent.setCreateTime(DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD_HH_MM_SS)).setBusinessRec(context.getBusinessRec());

        try {

            IDEMPOTENT_REPOSITORY.insert(idempotentEntity);

            return pointInfo.getJoinPoint().proceed();
        } catch (Exception ex) {

            throw new IdempotentInterceptorException(ex);
        }

    }

}
