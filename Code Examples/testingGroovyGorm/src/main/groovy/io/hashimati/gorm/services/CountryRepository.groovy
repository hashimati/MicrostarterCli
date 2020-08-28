package io.hashimati.gorm.services

import grails.gorm.services.Service
import groovy.transform.CompileStatic
import io.hashimati.gorm.domains.Country

@Service(Country)
@CompileStatic
interface CountryRepository {
    Country save(Country country)
    Country findById(Serializable id)
    List<Country> list();
    Long count()
    List<Country> findByName(String name);
}