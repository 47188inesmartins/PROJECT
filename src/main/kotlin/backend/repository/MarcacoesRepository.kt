package DAW.BattleShip.repository

import java.time.Instant
import java.util.*

interface MarcacoesRepository{
    fun getByEmpresa(empresa: String): Int
    fun getByCliente(cliente: String): Int
    fun remove(empresa: String, cliente: String, date: Date, hour: Date) : Int?
    fun add(empresa: String, cliente: String, date: Date, hour: Date) : Int?
}