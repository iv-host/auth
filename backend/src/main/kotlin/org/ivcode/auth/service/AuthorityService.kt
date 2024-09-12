package org.ivcode.auth.service

/**
 * Authority Service
 *
 * This service is responsible for managing application authorities.
 */
public class AuthorityService {

    /**
     * Sets the application's scoping format
     *
     * The default authority scope is "application:authority". However, this may not provide the scoping needed for
     * some applications. You may need add scopes to the authority format. For example,
     * "application:repository:authority" adds a repository scope to the authority format.
     *
     * This will allow users to be assigned authorities more dynamically. For example, a user may have the authority
     * "mvn:snapshot:*" which would allow access to all snapshot repository, or "mvn:*:read" which would allow read
     * access to all repositories.
     *
     * @param application the application name
     * @param scopeFormat the application's scope format
     */
    public fun setAuthorityScopes(application: String, scopeFormat: String) {
        TODO()
    }

    public fun createAuthority(application: String, authorityInfo: AuthorityInfo): Authority {
        TODO()
    }

    public fun getAuthorities(application: String): List<Authority> {
        TODO()
    }

    public fun getAuthorities(application: Application): List<Authority> {
        TODO()
    }

    public fun updateAuthority(application: String, authority: String, authorityInfo: AuthorityInfo) {
        TODO()
    }

    public fun deleteAuthority(application: String, authority: String) {
        TODO()
    }

    public fun deleteAuthority(application: Application, authority: String) {
        TODO()
    }
}

/**
 * Authority information
 * For creating and updating authorities
 */
public data class AuthorityInfo (
    val authority: String,
    val description: String
)

public data class ApplicationAuthorities (
    val application: Application,
    val scopeFormat: String,
    val authorities: List<Authority>
)

public data class Authority (
    val id: Long,
    val application: String,
    val authority: String,
    val description: String
)