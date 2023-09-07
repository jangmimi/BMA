package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ap4j.bma.model.entity.meamulReg.QMaemulRegEntity.maemulRegEntity;


@RequiredArgsConstructor
public class MaemulRegEntityRepositoryImpl implements MaemulRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MaemulRegEntity> findMaemulListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng, String[] tradeTypes) {
        BooleanExpression tradeTypeCondition = eqTradeType(tradeTypes);

        return queryFactory
                .selectFrom(maemulRegEntity)
                .where(
                        maemulRegEntity.latitude.goe(southWestLat),
                        maemulRegEntity.latitude.loe(northEastLat),
                        maemulRegEntity.longitude.goe(southWestLng),
                        maemulRegEntity.longitude.loe(northEastLng),
                        tradeTypeCondition
                )
                .fetch();
    }

    private BooleanExpression eqTradeType(String[] tradeTypes) {
        if (tradeTypes == null || tradeTypes.length == 0) {
            return null; // tradeTypes가 비어있으면 조건 무시
        }

        return maemulRegEntity.tradeType.in(tradeTypes);
    }


}
