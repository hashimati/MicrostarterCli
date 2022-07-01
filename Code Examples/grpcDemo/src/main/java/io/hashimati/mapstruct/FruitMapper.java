package io.hashimati.mapstruct;

import io.hashimati.FruitProto;
import io.hashimati.domains.Fruit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FruitMapper {

    FruitMapper INSTANCE = Mappers.getMapper(FruitMapper.class);



    Fruit fruitProtoToFruit(FruitProto fruitProto);



    FruitProto fruitToFruitProto(Fruit fruit);



}
