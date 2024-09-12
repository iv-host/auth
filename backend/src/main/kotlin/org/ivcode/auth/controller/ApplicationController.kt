package org.ivcode.auth.controller

import org.ivcode.auth.service.Application
import org.ivcode.auth.service.ApplicationInfo
import org.ivcode.auth.service.ApplicationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/applications")
internal class ApplicationController (
    private val applicationService: ApplicationService
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ApplicationController::class.java)
    }

    @PostMapping
    fun createApplication(application: ApplicationModel) {
        LOGGER.info("Create Application: {}", application)
        applicationService.createApplication(application.name, application.toDomain())
    }

    @GetMapping
    fun getApplications(): List<ApplicationModel> {
        LOGGER.info("Get Applications")
        return applicationService.getApplications().map { it.toModel() }
    }

    @GetMapping("/{name}")
    fun getApplication(
        @PathVariable name: String
    ): ApplicationModel? {
        LOGGER.info("Get Application: {}", name)
        return applicationService.getApplication(name)?.toModel()
    }

    @PutMapping("/{name}")
    fun updateApplication(
        @PathVariable name: String,
        @RequestBody application: ApplicationModel
    ) {
        LOGGER.info("Update Application: {}", name)
        applicationService.updateApplication(name, application.toDomain())
    }

    @DeleteMapping("/{name}")
    fun deleteApplication(
        @PathVariable name: String
    ) {
        LOGGER.info("Delete Application: {}", name)
        applicationService.deleteApplication(name)
    }

    private fun Application.toModel() = ApplicationModel(
        name = name,
        description = description,
        url = url
    )

    private fun ApplicationModel.toDomain() = ApplicationInfo (
        name = name,
        description = description,
        url = url
    )
}

internal data class ApplicationModel(
    val name: String,
    val description: String,
    val url: String
)