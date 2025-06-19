# ms-security-app

Configure Keycloak server endpoint in `src/main/resources/application.yml`.
Example values:
- keycloak.client.auth-server-url: `http://10.23.228.150:7780/`
- keycloak.client.id: `sicdiapp`
- keycloak.client.realm: `sso-sicie`
- spring.security.oauth2.resourceserver.jwt.issuer-uri: `http://10.23.228.150:7780/realms/sso-sicie`

## Database configuration

The application uses a Hikari connection pool. Example Oracle 19c settings:

```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = 10.23.228.150)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = RSISDOC)))
    username: DOC
    password: "***Sicdi2026+-*"
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: oracle.jdbc.OracleDriver
    connection-timeout: 20000
    idle-timeout: 300000
    max-lifetime: 1200000
    minimum-idle: 20
    maximum-pool-size: 100
    auto-commit: true
    read-only: true
    pool-name: SICDI-HikariPool
    hikari:
      connection-test-query: SELECT 1 FROM DUAL
      initialization-fail-timeout: 10000
      data-source-properties:
        cachePrepStmts: true
        prepStmtCacheSize: 250
        prepStmtCacheSqlLimit: 2048
  jpa:
    properties:
      hibernate:
        default_schema: DOC
        dialect: org.hibernate.dialect.Oracle12cDialect
```

