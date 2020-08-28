package io.hashimati.gorm.domains

import grails.gorm.annotation.Entity

@Entity
class Country {
    String name;
    static constraints = {
        name size: 5..15, blank: true, unique: true

    }
}
