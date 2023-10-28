package momosetkn.app

import momosetkn.infras.database.doma.contexts.Db
import momosetkn.infras.database.doma.contexts.transactionWithContext
import momosetkn.infras.repositories.DatabaseDaoImpl
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

/**
 * エントリーポイント
 */
fun main(args: Array<String>) {
    ConfigureLog.configLog()

    val log = getLog()

    log.info("hello world")

    startKoin { modules(appDependencies) }
    val db = Koin.get<Db>(named("MIGRATE_DB"))

    db.transactionWithContext {
        DatabaseDaoImpl(domaConfig).initialize()
    }
}
