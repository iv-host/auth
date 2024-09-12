package org.ivcode.auth.system.application

public data class Scope (
    val type: ScopeType,
    val name: String,
)

public enum class ScopeType {
    RESOURCE,
    PARAMETER
}
