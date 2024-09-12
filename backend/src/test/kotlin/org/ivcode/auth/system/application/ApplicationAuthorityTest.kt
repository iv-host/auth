package org.ivcode.auth.system.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class ApplicationAuthorityTest {

    @Test
    fun `parse - valid authority string returns correct ApplicationAuthority`() {
        val authorityString = "app:scope1:scope2:action"
        val result = ApplicationAuthority.parse(authorityString)
        assertEquals("app", result.application)
        assertEquals("action", result.action)
        assertEquals(2, result.scopes.size)
        assertEquals(Scope(ScopeType.RESOURCE, "scope1"), result.scopes[0])
        assertEquals(Scope(ScopeType.RESOURCE, "scope2"), result.scopes[1])
    }

    @Test
    fun `parse - authority string with parameter scope returns correct ApplicationAuthority`() {
        val authorityString = "app:scope1:scope2?:action"
        val result = ApplicationAuthority.parse(authorityString)
        assertEquals("app", result.application)
        assertEquals("action", result.action)
        assertEquals(2, result.scopes.size)
        assertEquals(Scope(ScopeType.RESOURCE, "scope1"), result.scopes[0])
        assertEquals(Scope(ScopeType.PARAMETER, "scope2"), result.scopes[1])
    }

    @Test
    fun `parse - authority string with no scopes returns correct ApplicationAuthority`() {
        val authorityString = "app:action"
        val result = ApplicationAuthority.parse(authorityString)
        assertEquals("app", result.application)
        assertEquals("action", result.action)
        assertTrue(result.scopes.isEmpty())
    }

    @Test
    fun `parse - authority string with empty parts throws Exception`() {
        val authorityString = "app::action"
        assertThrows(IllegalArgumentException::class.java) {
            ApplicationAuthority.parse(authorityString)
        }
    }

    @Test
    fun `parse - authority string with only application throws Exception`() {
        val authorityString = "app"
        assertThrows(IllegalArgumentException::class.java) {
            ApplicationAuthority.parse(authorityString)
        }
    }

    @Test
    fun `parse - empty application name throws Exception`() {
        val authorityString = ":scope1:action"
        assertThrows(IllegalArgumentException::class.java) {
            ApplicationAuthority.parse(authorityString)
        }
    }

    @Test
    fun `parse - empty action name throws Exception`() {
        val authorityString = "app:scope1:"
        assertThrows(IllegalArgumentException::class.java) {
            ApplicationAuthority.parse(authorityString)
        }
    }

    @Test
    fun `parse - empty parameter value throws Exception`() {
        val authorityString = "app:?:action"
        assertThrows(IllegalArgumentException::class.java) {
            ApplicationAuthority.parse(authorityString)
        }
    }
}