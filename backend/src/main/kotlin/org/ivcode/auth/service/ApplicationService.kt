package org.ivcode.auth.service

import org.ivcode.auth.repository.ApplicationDao
import org.ivcode.auth.repository.ApplicationEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


/**
 *
 */
@Service
public class ApplicationService (
    private val applicationDao: ApplicationDao
) {

    @Transactional(propagation = Propagation.REQUIRED)
    public fun createApplication(name: String, applicationInfo: ApplicationInfo): Application {
        val entity = applicationInfo.toEntity()
        applicationDao.createApplication(entity)
        return entity.toDomain()
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public fun getApplications(): List<Application> =
        applicationDao.getApplications().map { it.toDomain() }

    @Transactional(propagation = Propagation.SUPPORTS)
    public fun getApplication(name: String): Application? =
        applicationDao.getApplicationByName(name)?.toDomain()

    @Transactional(propagation = Propagation.REQUIRED)
    public fun updateApplication(name: String, applicationInfo: ApplicationInfo) {
        applicationDao.updateApplicationByName(name, applicationInfo.toEntity())
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public fun deleteApplication(name: String) {
        applicationDao.deleteApplicationByName(name)
    }

    private fun ApplicationEntity.toDomain() = Application(
        id = id!!,
        name = name!!,
        description = description!!,
        url = url!!
    )

    private fun ApplicationInfo.toEntity() = ApplicationEntity(
        name = name,
        description = description,
        url = url
    )
}

/**
 * An application
 */
public data class Application (
    /** The id of the application */
    val id: Long,
    /** The name of the application */
    val name: String,
    /** The description of the application */
    val description: String,
    /** A link to the application */
    val url: String
)

/**
 * Information about an application
 */
public data class ApplicationInfo (
    /** The name of the application */
    val name: String,
    /** The description of the application */
    val description: String,
    /** A link to the application */
    val url: String
)
