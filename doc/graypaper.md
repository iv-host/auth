## 01. Application Authority

Application Authorities are assigned to applications. It is the set of authorities available through the application.
They act as a blueprint for the *user authorities*. 

### Format
| Type              | Format                                   | Description                                  |
|-------------------|------------------------------------------|----------------------------------------------|
| `app_authority`   | `app` `scopes` `action`                  | app + scopes + acton                         |
| `app`             | `string` `delimiter`                     | application name + delimiter                 |
| `scopes`          | (`resource`\|`parameter`)*               | resource or parameter, zero or more          |
| `resource`        | `string` `delimiter`                     | resource name + delimiter                    |
| `parameter`       | `string` `param_indicator` `delimiter`   | parameter name + param indicator + delimiter |
| `action`          | `string`                                 | action name                                  |
| `delimiter`       | :                                        | delimiter between elements                   |
| `param_indicator` | ?                                        | parameter indicator                          |
| `string`          | [a-zA-Z0-9_]+                            | string literal                               |

### Application
Each application will have a unique name that is used to identify the application.

### Resources
A resource is a high-level object. A resource might be "repository", "user", or "group". Resources can be thought of as
containers for other resources or parameters. Resources are represented only by their name.

### Parameters
A parameter is a low-level object. A value that might only be known at runtime. For example, a parameter might be the
repository's name, a user's name, or group's name. Parameters are represented by their name in an *application
authority* and by their value in a *user authority*.

### Action
The action is the operation that is allowed. For example, an action might be "read", "write", or "delete". Actions
should always be in terms of what the user can do, never what the user cannot do.

### Application Authority Examples
```
mvn:repository:name?:read
mvn:repository:name?:write
mvn:admin:basic_auth:user?:create
mvn:admin:basic_auth:user?:delete
```
<sub>Note the `?` character. This is used to denote a parameter in the application authority.</sub>

### Conflict Situations
When configuring application authorities, it is important to avoid conflicts. If a conflict is detected, the
configuration will fail with an exception. A conflict occurs when there's possible ambiguity between two or more
application  authorities. For example, if the following application authorities are configured:

#### Conflict Example
```
mvn:repository:name?:read    // Allows read access to a repository
mvn:repository:list:read     // Allows read access to the list of repository names
```
In this example, it's impossible to determine which application authority should be used when the user requests access.
A given user might have access to a repository but not to the list of repositories. We could say if the value is
"list", then the user has access to the list of repositories and the repository "list". The fact that a single user
authority can match both application authorities is a conflict and cannot be allowed.


## 02. User Authorities

User authorities are assigned to users, verified against the *application authorities*. When a user requests access to a
resource, a runtime authority validation check is performed against the user's *user authorities*. The format of the
allowed user authorities is defined by the application authorities. The difference in format is that user authorities
will have values, rather than names, for parameters. *User authorities* also support wildcards.

### Wildcards

User authorities also support wildcards. Wildcards are used to generalize the user authorities.

*Wildcards*
 - `*` : Matches any single `parameter` or `action` (not valid on `application` or `resource` fields)
 - `**`: Matches any field and the subsequent fields (applicable to all fields)

### User Authority Examples
```
mvn:repository:*:read               // User has read access to any repository
mvn:repository:snapshot:write       // User has write access to the "snapshot" repository
mvn:admin:user:bob:read             // User has read access to the "bob" user
mvn:admin:user:**                   // User has any access to any user
```

## 03. Runtime Authority Validation

When a user requests access to a resource, the application will do a runtime authority validation check against the
user's user authorities. If the user has the required authority, validation will pass, and the user will be granted
access. If the user does not have the required authority, validation will fail, and the user will be denied access.

### Wildcard Matching
Like with user authorities, authority validation also support wildcards. However, conceptually, they are a little
different. Wildcards in user authorities are used to say a user has access to ALL of. Wildcards in validation are used
to verify that a user has access to ANY of.

Matching:
 - ALL matching - using a wildcard in a *user authority* - implies the user has access to all resources within the scope
 - ANY matching - using a wildcard in *authority validation* - implies the user has access to at least one resource within the scope

For example, consider this string "mvn:repository:*:read". If this represents a user authority, it means the user has
read access to ALL repositories. If this is an authority validation string, it means the user must have read access to
at least one repository.

### Examples
```
hasAuthority(':repository:*:read')         // Verifies the user has read access to any repository
hasAuthority(':repository:snapshot:read')  // Verifies the user has read access to the "snapshot" repository
hasAuthority(':repository:**')             // Verifies the user has access to any repository
hasAuthority(':repository:#repo:read')     // Verifies the user has read access to the repository defined by the "repo" variable
```