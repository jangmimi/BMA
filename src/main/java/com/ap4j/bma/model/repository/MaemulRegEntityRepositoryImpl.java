package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ap4j.bma.model.entity.meamulReg.QMaemulRegEntity.maemulRegEntity;


@RequiredArgsConstructor
public class MaemulRegEntityRepositoryImpl implements MaemulRepositoryCustom{
    private final JPAQueryFactory queryFactory;


    @Override
    public List<MaemulRegEntity> findMaemulListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng, String tradeTypes) {
        BooleanExpression tradeTypeCondition = null;

        if (!StringUtils.isEmpty(tradeTypes)) {
            // tradeTypes 문자열을 쉼표로 분리하여 배열로 변환
            String[] tradeTypeArray = tradeTypes.split(",");

            // 배열을 사용하여 in 조건 설정
            tradeTypeCondition = maemulRegEntity.tradeType.in(tradeTypeArray);
        }

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

//    private List<BooleanExpression> eqTradeType(List<String> tradeTypes) {
//        List<BooleanExpression> tradeTypeConditions = new ArrayList<>();
//
//        if (tradeTypes != null && !tradeTypes.isEmpty()) {
//            for (String tradeType : tradeTypes) {
//                tradeTypeConditions.add(maemulRegEntity.tradeType.eq(tradeType));
//            }
//        }
//
//        return tradeTypeConditions;
//    }


}
