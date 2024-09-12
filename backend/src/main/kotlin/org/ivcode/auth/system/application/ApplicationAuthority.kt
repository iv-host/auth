package org.ivcode.auth.system.application

/**
 * An application authority
 */
public data class ApplicationAuthority (
    val application: String,
    val action: String,
    val scopes: List<Scope>,
) {
    public companion object {
        public fun parse(applicationAuthority: String): ApplicationAuthority {
            val parts = applicationAuthority.split(":")
            require(parts.size >= 2) { "Invalid authority string: $applicationAuthority" }

            val application = parts.first()
            require(application.isNotEmpty()) { "Invalid application name in authority string: $applicationAuthority" }

            val action = parts.last()
            require(action.isNotEmpty()) { "Invalid action in authority string: $applicationAuthority" }

            val scopes = parts.drop(1).dropLast(1).map {
                require(it.isNotEmpty()) { "Invalid authority string: $applicationAuthority" }
                if (it.endsWith("?")) {
                    Scope(ScopeType.PARAMETER, it.dropLast(1).also { param ->
                        require(param.isNotEmpty()) { "Invalid authority string: $applicationAuthority" }
                    })
                } else {
                    Scope(ScopeType.RESOURCE, it)
                }
            }

            return ApplicationAuthority(application, action, scopes)
        }
    }
}
