package ${securityPackage}.token

import io.micronaut.security.token.reader.HttpHeaderTokenReader
import jakarta.inject.Singleton

@Singleton
class ApiKeyTokenReader : HttpHeaderTokenReader() {
    protected val prefix: String?
        protected get() = null

    companion object {
        protected val headerName = "API-HEADER"
            protected get() = Companion.field
    }
}