package momosetkn.app

import momosetkn.domain.Company
import momosetkn.domain.Employee
import momosetkn.domain.News
import momosetkn.domain.Product
import momosetkn.infras.database.ConnectionPoolDatasource
import momosetkn.infras.database.doma.contexts.Db
import momosetkn.infras.repositories.CompaniesRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.seasar.doma.jdbc.JdbcLogger
import org.seasar.doma.jdbc.dialect.Dialect
import org.seasar.doma.jdbc.dialect.MysqlDialect
import org.seasar.doma.jdbc.momosetkn.Slf4jJdbcLogger
import java.time.LocalDateTime
import java.util.*

val infrasDependencies = module {
    // Doma
    single { Db(get(), get(), get()) }
    single<Dialect> { MysqlDialect() }
    single<JdbcLogger> { Slf4jJdbcLogger() }
    single { ConnectionPoolDatasource() }
}
val repositoryDependencies = module {
    single { CompaniesRepository() }
}
val dummyDataDependencies = module {
    factory {
        Company(
            id = get(),
            name = get(),
            employees = get(named("List<Employee>")),
            news = get(named("List<News><")),
            products = get(named("<Product>")),
            updatedBy = get(),
            updatedAt = get(),
            createdBy = get(),
            createdAt = get(),
        )
    }
    factoryOf(::News)
    factoryOf(::Product)
    factoryOf(::Employee)
    factory<List<News>>(named("List<News><")) { listOf(get(), get(), get()) }
    factory<List<Product>>(named("<Product>")) { listOf(get(), get(), get()) }
    factory<List<Employee>>(named("List<Employee>")) { listOf(get(), get(), get()) }
    factory<String> { UUID.randomUUID().toString() }
    factory<LocalDateTime> {
        LocalDateTime.now().plusDays((Math.random() * 100).toLong())
    }
}

val appDependencies =
    listOf(
        infrasDependencies,
        repositoryDependencies,
        dummyDataDependencies,
    )
