package io.hashimati.gorm.services

import io.hashimati.gorm.domains.Country

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CountryService {

    @Inject
    CountryRepository countryRepository


    Country save(Country country){
        return countryRepository.save(country)
    }
    Country findById(Serializable id){

        return countryRepository.findById(id)
    }
    List<Country> findAll(){
        return countryRepository.list()
    }
    Long count(){
        return countryRepository.count()

    }

}