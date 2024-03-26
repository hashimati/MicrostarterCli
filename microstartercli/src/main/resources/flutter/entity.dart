
import 'dart:convert';

class ${entityName}
{

    ${instances}

    ${entityName}({${parameters}});

    factory ${entityName}.fromMap(Map<String, dynamic> map) {
        return ${entityName}(
            ${fromMap}
        );
    }

    Map<String, dynamic> toMap() {
        return {
            ${toMap}
        };
    }

    String toString() {
        return json.encode(this);
    }

    String toJson() => json.encode(toMap());

    factory AuthenticationResponse.fromJson(dynamic json) {
        return AuthenticationResponse(
            ${fromJson}
        );
    }

String toJson() => json.encode(toMap());
}