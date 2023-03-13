package backend.repository

import DAW.BattleShip.repository.MarcacoesRepository
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import java.time.Instant
import java.util.*

class JbdiMarcacoesRepository(private val handle: Handle): MarcacoesRepository {

    override fun getByEmpresa(empresa: String): Int {
        TODO("Not yet implemented")
    }

    override fun getByCliente(cliente: String): Int {
        TODO("Not yet implemented")
    }

    override fun remove(empresa: String, cliente: String, date: Date, hour: Date): Int? {
        TODO("Not yet implemented")
    }

    override fun add(empresa: String, cliente: String, date: Date, hour: Date): Int? {
        return handle.createQuery("insert into bdo.marcacoes(client, emp, date, hour) values (:empresa, :cliente, :date, :hour) returning id"
            ).bind("empresa", empresa)
            .bind("cliente", cliente)
            .bind("date", date)
            .bind("hour", hour)
            .mapTo<Int>()
            .first()
    }

}