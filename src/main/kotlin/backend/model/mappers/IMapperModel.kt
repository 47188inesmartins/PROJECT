package backend.model.mappers

/**
 * interface Mapper
 */


interface IMapperModel <Entity, Key> {

    fun add(entity : Entity): Key?
    fun remove(key : Key): Boolean
    fun get(key: Key): Entity

}