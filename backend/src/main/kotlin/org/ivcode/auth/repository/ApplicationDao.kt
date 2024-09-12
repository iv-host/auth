package org.ivcode.auth.repository

import org.apache.ibatis.annotations.*

/* --== SQL for Default Dialect ==-- */

private const val DEFAULT_GET_APPLICATIONS: String = """
 SELECT * FROM application
"""

private const val DEFAULT_GET_APPLICATION: String = """
 SELECT * FROM application WHERE id = #{id}
"""

public const val DEFAULT_GET_APPLICATION_BY_NAME: String = """
 SELECT * FROM application WHERE name = #{name}
"""

private const val DEFAULT_CREATE_APPLICATION: String = """
 INSERT INTO application (name, description, url)
 VALUES (#{name}, #{description}, #{url})
"""

private const val DEFAULT_UPDATE_APPLICATION: String = """
 UPDATE application
 SET name=#{application.name}, description=#{application.description}, url=#{application.url}
 WHERE id = #{id}
"""

private const val DEFAULT_UPDATE_APPLICATION_BY_NAME: String = """
 UPDATE application
 SET name=#{application.name}, description=#{application.description}, url=#{application.url}
 WHERE name = #{name}
"""

private const val DEFAULT_DELETE_APPLICATION: String = """
 DELETE FROM application WHERE id = #{id}
"""

private const val DEFAULT_DELETE_APPLICATION_BY_NAME: String = """
 DELETE FROM application WHERE name = #{name}
"""


@Mapper
public interface ApplicationDao {
    @Insert(DEFAULT_CREATE_APPLICATION)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public fun createApplication(application: ApplicationEntity)

    @Select(DEFAULT_GET_APPLICATIONS)
    public fun getApplications(): List<ApplicationEntity>

    @Select(DEFAULT_GET_APPLICATION)
    public fun getApplication(id: Long): ApplicationEntity?

    @Select(DEFAULT_GET_APPLICATION_BY_NAME)
    public fun getApplicationByName(name: String): ApplicationEntity?

    @Update(DEFAULT_UPDATE_APPLICATION)
    public fun updateApplication(id: Long, application: ApplicationEntity)

    @Update(DEFAULT_UPDATE_APPLICATION_BY_NAME)
    public fun updateApplicationByName(name: String, application: ApplicationEntity)

    @Delete(DEFAULT_DELETE_APPLICATION)
    public fun deleteApplication(id: Long)

    @Delete(DEFAULT_DELETE_APPLICATION_BY_NAME)
    public fun deleteApplicationByName(name: String)
}

public data class ApplicationEntity (
    /** The id of the application */
    val id: Long? = null,
    /** The name of the application */
    val name: String? = null,
    /** The description of the application */
    val description: String? = null,
    /** A link to the application */
    val url: String? = null
)