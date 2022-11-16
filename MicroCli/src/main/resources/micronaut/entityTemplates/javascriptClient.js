import axios from "axios/dist/axios.js";

let ${Entity}Service ={

    findAll(fn) {
        axios.get('http://localhost:3030/api/v1/${entity}/findAll'
            ,
            {
                headers: {
                    'Authorization': token
                 }
            }
        ).then(response => fn(response)).catch(error => console.log(error))
    },
    findAllNames(fn) {
        axios.get('http://localhost:3030/api/v1/${entity}/findAllNames'
             ,
             {
                 headers: {
                     'Authorization': token
                 }
             }
        ).then(response => fn(response)).catch(error => console.log(error))
    },

    findById(id, fn) {
        axios
            .get('http://localhost:3030/api/v1/${entity}/get?id=' + id
                 ,
                 {
                     headers: {
                         'Authorization': token
                     }
                 }
            )
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    create(${entity}, fn) {

        axios
            .post('http://localhost:3030/api/v1/${entity}/save', ${entity},
              {
                                 headers: {
                                     'Authorization': token
                                 }
             })
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    update(id, ${entity}, fn) {
        axios
            .put('http://localhost:3030/api/v1/${entity}/update', ${entity}
                 ,
                 {
                     headers: {
                         'Authorization': token
                     }
                 }
            )
            .then(response => fn(response))
            .catch(error => console.log(error))
    },

    delete(id, fn) {
        axios
            .delete('http://localhost:3030/api/v1/${entity}/delete/' + id
                 ,
                 {
                     headers: {
                         'Authorization': token
                     }
                 }
            )
            .then(response => fn(response))
            .catch(error => console.log(error))
    }
}

export default ${Entity}Service;
